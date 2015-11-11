package dubbo;

public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "hello "+ name;
		
	}

}
