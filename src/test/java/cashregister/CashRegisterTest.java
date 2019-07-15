package cashregister;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CashRegisterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream outputStream = System.out;

    @BeforeEach
    void setOutputStream() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(outputStream);
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {

        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        Item[] items = new Item[]{new Item("fish", 18)};
        Purchase purchase = new Purchase(items);

        cashRegister.process(purchase);

        assertEquals(purchase.asString(), outContent.toString());
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {

        Printer printer = new Printer();
        Purchase purchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(printer);
        when(purchase.asString()).thenReturn("fish\t18\n");

        cashRegister.process(purchase);

        assertEquals("fish\t18\n", outContent.toString());
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        Printer printer = mock(Printer.class);
        Purchase purchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(printer);


        cashRegister.process(purchase);

        verify(printer).print(purchase.asString());
    }

}
