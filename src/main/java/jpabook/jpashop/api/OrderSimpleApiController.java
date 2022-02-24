package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * xToOne()ManyToOne, OneToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for(Order order:all){
            order.getMember().getName();
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("api/v2/simple-orders")
    public List<OrderSimpleQueryDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderSimpleQueryDto> result = orders.stream()
                .map(o -> new OrderSimpleQueryDto(o.getId(),o.getMember().getName(), o.getOrderDate(), o.getStatus(), o.getMember().getAddress()))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("api/v3/simple-orders")
    public List<OrderSimpleQueryDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<OrderSimpleQueryDto> result = orders.stream()
                .map(o -> new OrderSimpleQueryDto(o.getId(),o.getMember().getName(), o.getOrderDate(), o.getStatus(), o.getMember().getAddress()))
                .collect(Collectors.toList());
        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }


}
