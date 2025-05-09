package aiss.gitminer.gitminer;
import aiss.gitminer.exceptions.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.controller.CommentController;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentControllerTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentController commentController;

    public CommentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById_exists() throws CommentNotFoundException {
        Comment mockComment = new Comment();
        mockComment.setId("123");

        when(commentRepository.findById("123")).thenReturn(Optional.of(mockComment));

        Comment result = commentController.findById("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    public void testFindById_notFound() {
        when(commentRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class, () -> {
            commentController.findById("999");
        });
    }
}
