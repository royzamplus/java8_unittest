package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by liudi on 5/10/15.
 */
public class AnswerCollectionTest {
    private AnswerCollection answers;

    @Before
    public void createProfile() {
        answers = new AnswerCollection();
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream()
                .mapToInt(a -> a.getQuestion().getId()).toArray();
    }

    @Test
    public void findsAnswersBasedOnPredicate() {
        answers.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        answers.add(new Answer(new PercentileQuestion(2, "2", new String[] {}), 0));
        answers.add(new Answer(new PercentileQuestion(3, "3", new String[] {}), 0));

        List<Answer> result =
                answers.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);

        assertThat(ids(result), equalTo(new int[] { 2, 3 }));
    }
}
