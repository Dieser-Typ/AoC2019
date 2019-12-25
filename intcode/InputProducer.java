package intcode;

@FunctionalInterface
public interface InputProducer {
    long produceInput(IntCodeComputer computer);
}
