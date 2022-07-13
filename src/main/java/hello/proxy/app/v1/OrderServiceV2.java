package hello.proxy.app.v1;

public class OrderServiceV2 implements OrderServiceV1{
    
    private final OrderRepositoryV2 repository;
    
    public OrderServiceV2(OrderRepositoryV2 repositoryV1) {
        this.repository = repositoryV1;
    }
    
    @Override
    public void orderItem(String itemId) {
        repository.save(itemId);
    }
}
