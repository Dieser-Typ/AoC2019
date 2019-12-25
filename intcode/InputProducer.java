package intcode;

@FunctionalInterface
public interface InputProducer {
    int produceInput(IntCodeComputer computer);
}
