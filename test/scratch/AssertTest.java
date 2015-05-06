package scratch;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liudi on 4/27/15.
 */
public class AssertTest {

    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }

        private static final long serialVersionUID = 1L;
    }

    class Account {
        int balance;
        String name;

        Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }
    }

    class Customer {
        List<Account> accounts = new ArrayList<>();

        void add(Account account) {
            accounts.add(account);
        }

        Iterator<Account> getAccounts() {
            return accounts.iterator();
        }
    }

    private Account account;

    @Before
    public void createAccount() {
        account = new Account("an account name");
    }

    @Test
    public void hasPositiveBalance() {
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
        assertThat(account.getBalance(), equalTo(100));
    }

    @Test
    public void depositIncreasesBalance_hamcrestAssertTrue() {
        account.deposit(50);
        assertThat(account.getBalance() > 0, is(true));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void comparesArraysFailing() {
        assertThat(new String[] {"a", "b", "c"}, equalTo(new String[]{"a", "b"}));
    }

    @Test
    public void comparesArraysPassing() {
        assertThat(new String[] {"a", "b"}, equalTo(new String[]{"a", "b"}));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void comparesCollectionsFailing() {
        assertThat(Arrays.asList(new String[] {"a"}),
                equalTo(Arrays.asList(new String[] {"a", "ab"})));
    }

    @Test
    public void comparesCollectionsPassing() {
        assertThat(Arrays.asList(new String[]{"a"}),
                equalTo(Arrays.asList(new String[] {"a"})));
    }

    @Ignore
    @Test
    public void testWithWorthlessAssertionComment() {
        account.deposit(50);
        assertThat("account balance is 100", account.getBalance(), equalTo(50));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void assertFailure() {
        assertTrue(account.getName().startsWith("xyz"));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void matchesFailure() {
        assertThat(account.getName(), startsWith("xyz"));
    }

    @Test
    public void variousMatcherTests() {
        Account account = new Account("my big fat acct");
        assertThat(account.getName(), is(equalTo("my big fat acct")));
        assertThat(account.getName(), allOf(startsWith("my"), endsWith("acct")));
        assertThat(account.getName(), anyOf(startsWith("my"), endsWith("acct")));
        assertThat(account.getName(), not(equalTo("plunderings")));
        assertThat(account.getName(), is(not(nullValue())));
        assertThat(account.getName(), is(notNullValue()));
        assertThat(account.getName(), isA(String.class));

        assertThat(account.getName(), is(notNullValue()));
        assertThat(account.getName(), equalTo("my big fat acct"));
    }

    @Test
    public void sameInstance() {
        Account a = new Account("a");
        Account aPrime = new Account("a");
        assertThat(a, not(org.hamcrest.CoreMatchers.sameInstance(aPrime)));
    }

    @Test
    public void moreMatcherTests() {
        Account account = new Account(null);
        assertThat(account.getName(), is(nullValue()));
    }

    @Ignore
    @ExpectToFail
    @Test
    public void classicAssertions() {
        Account account = new Account("acct namex");
        assertEquals("acct name", account.getName());
    }

    @Test(expected = InsufficientFundsException.class)
    public void throwsWhenWithdrawingTooMuch() {
        account.withdraw(100);
    }

    @Test
    public void throwsWhenWithdrawingTooMuchTry() {
        try {
            account.withdraw(100);
            fail();
        }
        catch (InsufficientFundsException expected) {
            assertThat(expected.getMessage(), equalTo("balance only 0"));
        }
    }

    @Test
    public void readsFromTestFile() throws IOException {
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
    }

    @After
    public void deleteForReadsFromTestFile() {
        new File("test.txt").delete();
    }

    @Test
    @Ignore("don't forget me!")
    public void somethingWeCannotHandleRightNow() {
        // ...
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionRule() {
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("balance only 0");

        account.withdraw(100);
    }

    @Test
    public void doubles() {
        assertEquals(9.7, 10.0 - 0.3, 0.005);
    }
}
