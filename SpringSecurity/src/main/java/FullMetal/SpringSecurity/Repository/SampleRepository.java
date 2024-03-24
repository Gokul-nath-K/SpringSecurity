package FullMetal.SpringSecurity.Repository;

import FullMetal.SpringSecurity.Models.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository  extends JpaRepository<Sample, Long> {


}
