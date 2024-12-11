package org.caansoft.sdfood.serviceimpl;

import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.model.User;
import org.caansoft.core.service.UserService;
import org.caansoft.sdfood.dto.OrderDto;
import org.caansoft.sdfood.dto.StockCountHeaderDto;
import org.caansoft.sdfood.dto.StockCountLineItemDto;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.enums.StockCountStatus;
import org.caansoft.sdfood.model.StockCountHeader;
import org.caansoft.sdfood.model.StockCountLineItem;
import org.caansoft.sdfood.model.StockStatusHistory;
import org.caansoft.sdfood.repo.StockCountLineItemRepository;
import org.caansoft.sdfood.repo.StockCountRepository;
import org.caansoft.sdfood.service.StockCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Service
public class StockCountServiceImpl implements StockCountService {

    @Autowired
    private StockCountRepository stockCountRepository;
    @Autowired
    private StockCountLineItemRepository stockCountLineItemRepository;
    @Autowired
    private UserService userService;

    @Override
    public <T extends BaseDto> T initiateStockCount() {
        StockCountHeaderDto headerDto = null;
        try {

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.isEmpty() || username.equals("anonymousUser")) {
                throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
            }
            StockCountHeader header = new StockCountHeader();
            header.setStatus(StockCountStatus.NEW);
            StockStatusHistory statusHistory = new StockStatusHistory();
            statusHistory.setStockCountHeader(header);
            statusHistory.setStatus(header.getStatus());
            statusHistory.setStartDate(Timestamp.from(Instant.now()));
            header.setStockStatusHistories(Arrays.asList(statusHistory));

            header = stockCountRepository.save(header);
            headerDto = ModelDtoConvertor.STOCK_COUNT.modelToDto(header, StockCountHeaderDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T) headerDto;
    }

    @Override
    public <T extends BaseDto> T addLineItem(Long stockCountId, StockCountLineItemDto lineItem) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        StockCountLineItem model = ModelDtoConvertor.STOCK_COUNT_LINEITEM.dtoToModel(lineItem, StockCountLineItem.class);
        StockCountHeader header = new StockCountHeader();
        header.setId(stockCountId);
        model.setStockCountHeader(header);
        model = stockCountLineItemRepository.save(model);
        return ModelDtoConvertor.STOCK_COUNT_LINEITEM.modelToDto(model, StockCountLineItemDto.class);
    }

    @Override
    public <T extends BaseDto> T update(Long stockCountId, StockCountHeaderDto dto) {
        StockCountHeaderDto headerDto = null;
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.isEmpty() || username.equals("anonymousUser")) {
                throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
            }
            ModelDtoConvertor.setStockCountRepository(stockCountRepository);
            StockCountHeader model = ModelDtoConvertor.STOCK_COUNT.dtoToModel(dto, StockCountHeader.class);
            model = stockCountRepository.save(model);
            headerDto = ModelDtoConvertor.STOCK_COUNT.modelToDto(model, StockCountHeaderDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T) headerDto;
    }

    @Override
    public <T extends BaseDto> T add(T t) {
        return null;
    }

    @Override
    public <T extends BaseDto> T update(T t) {
        return null;
    }

    @Override
    public <T extends BaseDto> Page<T> find(Pageable pageable) {
        return (Page<T>) new PageImpl<OrderDto>(Collections.EMPTY_LIST, pageable, 0);
    }

    @Override
    public <T extends BaseDto> Page<T> find(StockCountHeader stockCountHeader, Date fromDate, Date toDate, StockCountStatus status, Pageable pageable) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.isEmpty() || username.equals("anonymousUser")) {
            throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
        }
        User loggedInUser = userService.findUser(username);

        if (loggedInUser.getEmployee() == null || loggedInUser.getEmployee().getJob() == null) {
            throw new CoreRuntimeException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<StockCountHeader> example = Example.of(stockCountHeader, matcher);

        Page<StockCountHeader> stockCountHeaders = null;

        stockCountHeaders = stockCountRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, Example.of(stockCountHeader, matcher), status), pageable);

        List<StockCountHeaderDto> stockCountHeaderDtos = new ArrayList<>();
        for (StockCountHeader dbOrder : stockCountHeaders) {
            try {
                stockCountHeaderDtos.add(ModelDtoConvertor.STOCK_COUNT.modelToDto(dbOrder, StockCountHeaderDto.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (Page<T>) new PageImpl<StockCountHeaderDto>(stockCountHeaderDtos, pageable, stockCountHeaders.getTotalElements());
    }

    @Override
    public <T extends BaseDto> T findOne(long id) {
        Optional<StockCountHeader> optional = stockCountRepository.findById(id);
        if (optional.isPresent()) {
            try {
                return ModelDtoConvertor.STOCK_COUNT.modelToDto(optional.get(), StockCountHeaderDto.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void delete(long id) {
        StockCountHeader order = stockCountRepository.findById(id).get();
        order.setFlag(Flag.DELETED);
        stockCountRepository.save(order);

    }

    private Specification<StockCountHeader> getSpecFromDatesAndExample(Date fromDate, Date toDate, Example<StockCountHeader> example, StockCountStatus status) {

        return (Specification<StockCountHeader>) (root, query, builder) -> {

            final List<Predicate> predicates = new ArrayList<>();

            if (fromDate != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdOn"), fromDate));
            }
            if (toDate != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdOn"), toDate));
            }
            if (status != null) {
                predicates.add(builder.equal(root.get("status"), status));
            } else {
                predicates.add(root.get("status").in(Arrays.asList(StockCountStatus.values())));
            }
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
