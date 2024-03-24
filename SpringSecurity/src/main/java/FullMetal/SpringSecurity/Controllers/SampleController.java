package FullMetal.SpringSecurity.Controllers;

import FullMetal.SpringSecurity.Models.Sample;
import FullMetal.SpringSecurity.Repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sample")
public class SampleController {

    private final SampleRepository sampleRepository;

    @GetMapping("/get")
    public List<Sample> get() {

        return sampleRepository.findAll();
    }

    @PostMapping("/post")
    public Sample post(@RequestBody Sample sample) {

        return sampleRepository.save(sample);
    }
}
