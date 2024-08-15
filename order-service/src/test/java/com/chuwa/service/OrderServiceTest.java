package com.chuwa.service;

import com.chuwa.entity.Order;
import com.chuwa.entity.OrderPrimaryKey;
import com.chuwa.po.Address;
import com.chuwa.po.OrderStatusEnum;
import com.chuwa.po.Payment;
import com.chuwa.repository.OrderRepository;
import com.datastax.oss.driver.api.core.cql.PagingState;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void createOrder() {
        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertNotNull(result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void findOrderByOrderId() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        when(orderRepository.findBySearchId(orderId)).thenReturn(Optional.of(order));

        Order result = orderService.findOrderByOrderId(orderId);

        assertNotNull(result);
        verify(orderRepository, times(1)).findBySearchId(orderId);
    }

    @Test
    void testFindOrderByOrderId_NotFound() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findBySearchId(orderId)).thenReturn(Optional.empty());

        Order result = orderService.findOrderByOrderId(orderId);

        assertNull(result);
        verify(orderRepository, times(1)).findBySearchId(orderId);
    }

    @Test
    void testFindOrderByKey() {
        OrderPrimaryKey key = new OrderPrimaryKey(UUID.randomUUID(), LocalDate.now(), new Date());
        Order order = new Order();
        when(orderRepository.findById(key)).thenReturn(Optional.of(order));

        Order result = orderService.findOrderByKey(key);

        assertNotNull(result);
        verify(orderRepository, times(1)).findById(key);
    }

    @Test
    void testFindOrderByKey_NotFound() {
        OrderPrimaryKey key = new OrderPrimaryKey(UUID.randomUUID(), LocalDate.now(), new Date());
        when(orderRepository.findById(key)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.findOrderByKey(key);
        });

        assertEquals("Order not found", exception.getMessage());
        verify(orderRepository, times(1)).findById(key);
    }

    @Test
    void testCancelOrder() {
//        OrderPrimaryKey key = new OrderPrimaryKey(UUID.randomUUID(), LocalDate.now(), new Date());
//        Order order = new Order();
//        order.setOrderStatus(OrderStatusEnum.CREATED);
//        when(orderRepository.findById(key)).thenReturn(Optional.of(order));
//
//        String result = orderService.cancelOrder(key);
//
//        assertEquals("dont know", result);
//        verify(orderRepository, times(1)).findById(key);
    }

    @Test
    void testUpdateOrderPayment() {
        Payment payment = new Payment();
        UUID orderId = UUID.randomUUID();
        LocalDate createDate = LocalDate.now();
        Date timestamp = new Date();
        payment.setOrderId(orderId);
        payment.setCreatedDate(createDate);
        payment.setTimestamp(timestamp);

        Order order = new Order();
        order.setVersion(1);

        when(orderRepository.findById(any(OrderPrimaryKey.class))).thenReturn(Optional.of(order));

        ResultSet resultSet = mock(ResultSet.class);
        Row row = mock(Row.class);
        when(resultSet.one()).thenReturn(row);
        when(row.getBoolean("[applied]")).thenReturn(true);

        when(orderRepository.updatePaymentIfVersionMatches(eq(orderId), eq(createDate), eq(payment), eq(timestamp), eq(1)))
                .thenReturn(resultSet);

        boolean result = orderService.updateOrderPayment(payment);

        assertTrue(result);
        verify(orderRepository, times(1)).findById(any(OrderPrimaryKey.class));
        verify(orderRepository, times(1)).updatePaymentIfVersionMatches(eq(orderId), eq(createDate), eq(payment), eq(timestamp), eq(1));
    }

    @Test
    void testUpdateOrderAddress() {
        Address address = new Address();
        UUID orderId = UUID.randomUUID();
        LocalDate createDate = LocalDate.now();
        Date timestamp = new Date();
        address.setOrderId(orderId);
        address.setCreatedDate(createDate);
        address.setTimestamp(timestamp);

        Order order = new Order();
        order.setVersion(1);

        when(orderRepository.findById(any(OrderPrimaryKey.class))).thenReturn(Optional.of(order));

        ResultSet resultSet = mock(ResultSet.class);
        Row row = mock(Row.class);
        when(resultSet.one()).thenReturn(row);
        when(row.getBoolean("[applied]")).thenReturn(true);

        when(orderRepository.updateAddressIfVersionMatches(eq(orderId), eq(createDate), eq(address), eq(timestamp), eq(1)))
                .thenReturn(resultSet);

        boolean result = orderService.updateOrderAddress(address);

        assertTrue(result);
        verify(orderRepository, times(1)).findById(any(OrderPrimaryKey.class));
        verify(orderRepository, times(1)).updateAddressIfVersionMatches(eq(orderId), eq(createDate), eq(address), eq(timestamp), eq(1));
    }

    @Test
    void testUpdateOrderStatus() {
        OrderPrimaryKey key = new OrderPrimaryKey(UUID.randomUUID(), LocalDate.now(), new Date());
        Order order = new Order();
        order.setVersion(1);
        when(orderRepository.findById(key)).thenReturn(Optional.of(order));

        ResultSet resultSet = mock(ResultSet.class);
        Row row = mock(Row.class);
        when(resultSet.one()).thenReturn(row);
        when(row.getBoolean("[applied]")).thenReturn(true);

        when(orderRepository.updateStatusIfVersionMatches(eq(key.getOrderId()), eq(key.getCreatedDate()), eq(OrderStatusEnum.PAID), eq(key.getTimestamp()), eq(1)))
                .thenReturn(resultSet);

        boolean result = orderService.updateOrderStatus(key, OrderStatusEnum.PAID);

        assertTrue(result);
        verify(orderRepository, times(1)).findById(key);
        verify(orderRepository, times(1)).updateStatusIfVersionMatches(eq(key.getOrderId()), eq(key.getCreatedDate()), eq(OrderStatusEnum.PAID), eq(key.getTimestamp()), eq(1));
    }

    @Test
    void testGetPageOfOrders_FirstPage() {
        UUID userId = UUID.randomUUID();
        int page = 0;
        int size = 10;

        Slice<Order> orderSlice = mock(Slice.class);
        when(orderRepository.findByCustomerId(eq(userId), any(PageRequest.class))).thenReturn(orderSlice);

        Slice<Order> result = orderService.getPageOfOrders(userId, page, size, null);

        assertNotNull(result);
        verify(orderRepository, times(1)).findByCustomerId(eq(userId), any(PageRequest.class));
    }

    @Test
    void testGetPageOfOrders_WithPagingState() {
//        UUID userId = UUID.randomUUID();
//        int page = 1;
//        int size = 10;
//        String pagingState = "dummyPagingState";
//
//        PagingState state = PagingState.fromString(pagingState);
//        CassandraPageRequest pageRequest = CassandraPageRequest.of(PageRequest.of(page, size), state.getRawPagingState());
//
//        Slice<Order> orderSlice = mock(Slice.class);
//        when(orderRepository.findByCustomerId(eq(userId), eq(pageRequest))).thenReturn(orderSlice);
//
//        Slice<Order> result = orderService.getPageOfOrders(userId, page, size, pagingState);
//
//        assertNotNull(result);
//        verify(orderRepository, times(1)).findByCustomerId(eq(userId), eq(pageRequest));
    }
}