package backend.storage.api.service;

import backend.storage.api.service.impl.ReaderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReaderServiceTest {
    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private ReaderServiceImpl readerService;

    @Test
    public void readFromFile_validInput_shouldReturnListOfString() throws IOException {
        String content = "name;price;weight;productType;description;number" +
                "\nДиск гальмівний;24.99;45.00;1;descDisc;123";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        when(mockFile.getInputStream()).thenReturn(inputStream);
        List<String> result = readerService.readFromFile(mockFile);

        assertThat(result).containsExactly("name;price;weight;productType;description;number"
                , "Диск гальмівний;24.99;45.00;1;descDisc;123");
    }

    @Test
    public void readFromFile_IOError_ThrowsRuntimeException() throws IOException {

        when(mockFile.getInputStream()).thenThrow(new IOException("Test IOException"));

        // Act + Assert
        assertThrows(RuntimeException.class, () -> {
            readerService.readFromFile(mockFile);
        });
    }
}
