package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Bean type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Beans", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Bean implements Model {
  public static final QueryField ID = field("Bean", "id");
  public static final QueryField FLAVORS = field("Bean", "flavors");
  public static final QueryField STATUS = field("Bean", "status");
  public static final QueryField NAME = field("Bean", "name");
  public static final QueryField PROCESS = field("Bean", "process");
  public static final QueryField ORIGIN = field("Bean", "origin");
  public static final QueryField REGION = field("Bean", "region");
  public static final QueryField ALTITUDE_LOW = field("Bean", "altitude_low");
  public static final QueryField ALTITUDE_HIGH = field("Bean", "altitude_high");
  public static final QueryField MOISTURE = field("Bean", "moisture");
  public static final QueryField VARIETY = field("Bean", "variety");
  public static final QueryField DENSITY = field("Bean", "density");
  public static final QueryField GRADE = field("Bean", "grade");
  public static final QueryField IMAGE = field("Bean", "image");
  public static final QueryField DEALER = field("Bean", "dealer");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID") List<String> flavors;
  private final @ModelField(targetType="Status", isRequired = true) Status status;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String process;
  private final @ModelField(targetType="String") String origin;
  private final @ModelField(targetType="String") String region;
  private final @ModelField(targetType="String") String altitude_low;
  private final @ModelField(targetType="String") String altitude_high;
  private final @ModelField(targetType="String") String moisture;
  private final @ModelField(targetType="String") String variety;
  private final @ModelField(targetType="String") String density;
  private final @ModelField(targetType="String") String grade;
  private final @ModelField(targetType="String") String image;
  private final @ModelField(targetType="ID") String dealer;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public List<String> getFlavors() {
      return flavors;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public String getName() {
      return name;
  }
  
  public String getProcess() {
      return process;
  }
  
  public String getOrigin() {
      return origin;
  }
  
  public String getRegion() {
      return region;
  }
  
  public String getAltitudeLow() {
      return altitude_low;
  }
  
  public String getAltitudeHigh() {
      return altitude_high;
  }
  
  public String getMoisture() {
      return moisture;
  }
  
  public String getVariety() {
      return variety;
  }
  
  public String getDensity() {
      return density;
  }
  
  public String getGrade() {
      return grade;
  }
  
  public String getImage() {
      return image;
  }
  
  public String getDealer() {
      return dealer;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Bean(String id, List<String> flavors, Status status, String name, String process, String origin, String region, String altitude_low, String altitude_high, String moisture, String variety, String density, String grade, String image, String dealer) {
    this.id = id;
    this.flavors = flavors;
    this.status = status;
    this.name = name;
    this.process = process;
    this.origin = origin;
    this.region = region;
    this.altitude_low = altitude_low;
    this.altitude_high = altitude_high;
    this.moisture = moisture;
    this.variety = variety;
    this.density = density;
    this.grade = grade;
    this.image = image;
    this.dealer = dealer;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Bean bean = (Bean) obj;
      return ObjectsCompat.equals(getId(), bean.getId()) &&
              ObjectsCompat.equals(getFlavors(), bean.getFlavors()) &&
              ObjectsCompat.equals(getStatus(), bean.getStatus()) &&
              ObjectsCompat.equals(getName(), bean.getName()) &&
              ObjectsCompat.equals(getProcess(), bean.getProcess()) &&
              ObjectsCompat.equals(getOrigin(), bean.getOrigin()) &&
              ObjectsCompat.equals(getRegion(), bean.getRegion()) &&
              ObjectsCompat.equals(getAltitudeLow(), bean.getAltitudeLow()) &&
              ObjectsCompat.equals(getAltitudeHigh(), bean.getAltitudeHigh()) &&
              ObjectsCompat.equals(getMoisture(), bean.getMoisture()) &&
              ObjectsCompat.equals(getVariety(), bean.getVariety()) &&
              ObjectsCompat.equals(getDensity(), bean.getDensity()) &&
              ObjectsCompat.equals(getGrade(), bean.getGrade()) &&
              ObjectsCompat.equals(getImage(), bean.getImage()) &&
              ObjectsCompat.equals(getDealer(), bean.getDealer()) &&
              ObjectsCompat.equals(getCreatedAt(), bean.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), bean.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFlavors())
      .append(getStatus())
      .append(getName())
      .append(getProcess())
      .append(getOrigin())
      .append(getRegion())
      .append(getAltitudeLow())
      .append(getAltitudeHigh())
      .append(getMoisture())
      .append(getVariety())
      .append(getDensity())
      .append(getGrade())
      .append(getImage())
      .append(getDealer())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Bean {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("flavors=" + String.valueOf(getFlavors()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("process=" + String.valueOf(getProcess()) + ", ")
      .append("origin=" + String.valueOf(getOrigin()) + ", ")
      .append("region=" + String.valueOf(getRegion()) + ", ")
      .append("altitude_low=" + String.valueOf(getAltitudeLow()) + ", ")
      .append("altitude_high=" + String.valueOf(getAltitudeHigh()) + ", ")
      .append("moisture=" + String.valueOf(getMoisture()) + ", ")
      .append("variety=" + String.valueOf(getVariety()) + ", ")
      .append("density=" + String.valueOf(getDensity()) + ", ")
      .append("grade=" + String.valueOf(getGrade()) + ", ")
      .append("image=" + String.valueOf(getImage()) + ", ")
      .append("dealer=" + String.valueOf(getDealer()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static StatusStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Bean justId(String id) {
    return new Bean(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      flavors,
      status,
      name,
      process,
      origin,
      region,
      altitude_low,
      altitude_high,
      moisture,
      variety,
      density,
      grade,
      image,
      dealer);
  }
  public interface StatusStep {
    BuildStep status(Status status);
  }
  

  public interface BuildStep {
    Bean build();
    BuildStep id(String id);
    BuildStep flavors(List<String> flavors);
    BuildStep name(String name);
    BuildStep process(String process);
    BuildStep origin(String origin);
    BuildStep region(String region);
    BuildStep altitudeLow(String altitudeLow);
    BuildStep altitudeHigh(String altitudeHigh);
    BuildStep moisture(String moisture);
    BuildStep variety(String variety);
    BuildStep density(String density);
    BuildStep grade(String grade);
    BuildStep image(String image);
    BuildStep dealer(String dealer);
  }
  

  public static class Builder implements StatusStep, BuildStep {
    private String id;
    private Status status;
    private List<String> flavors;
    private String name;
    private String process;
    private String origin;
    private String region;
    private String altitude_low;
    private String altitude_high;
    private String moisture;
    private String variety;
    private String density;
    private String grade;
    private String image;
    private String dealer;
    @Override
     public Bean build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Bean(
          id,
          flavors,
          status,
          name,
          process,
          origin,
          region,
          altitude_low,
          altitude_high,
          moisture,
          variety,
          density,
          grade,
          image,
          dealer);
    }
    
    @Override
     public BuildStep status(Status status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep flavors(List<String> flavors) {
        this.flavors = flavors;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep process(String process) {
        this.process = process;
        return this;
    }
    
    @Override
     public BuildStep origin(String origin) {
        this.origin = origin;
        return this;
    }
    
    @Override
     public BuildStep region(String region) {
        this.region = region;
        return this;
    }
    
    @Override
     public BuildStep altitudeLow(String altitudeLow) {
        this.altitude_low = altitudeLow;
        return this;
    }
    
    @Override
     public BuildStep altitudeHigh(String altitudeHigh) {
        this.altitude_high = altitudeHigh;
        return this;
    }
    
    @Override
     public BuildStep moisture(String moisture) {
        this.moisture = moisture;
        return this;
    }
    
    @Override
     public BuildStep variety(String variety) {
        this.variety = variety;
        return this;
    }
    
    @Override
     public BuildStep density(String density) {
        this.density = density;
        return this;
    }
    
    @Override
     public BuildStep grade(String grade) {
        this.grade = grade;
        return this;
    }
    
    @Override
     public BuildStep image(String image) {
        this.image = image;
        return this;
    }
    
    @Override
     public BuildStep dealer(String dealer) {
        this.dealer = dealer;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, List<String> flavors, Status status, String name, String process, String origin, String region, String altitudeLow, String altitudeHigh, String moisture, String variety, String density, String grade, String image, String dealer) {
      super.id(id);
      super.status(status)
        .flavors(flavors)
        .name(name)
        .process(process)
        .origin(origin)
        .region(region)
        .altitudeLow(altitudeLow)
        .altitudeHigh(altitudeHigh)
        .moisture(moisture)
        .variety(variety)
        .density(density)
        .grade(grade)
        .image(image)
        .dealer(dealer);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder flavors(List<String> flavors) {
      return (CopyOfBuilder) super.flavors(flavors);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder process(String process) {
      return (CopyOfBuilder) super.process(process);
    }
    
    @Override
     public CopyOfBuilder origin(String origin) {
      return (CopyOfBuilder) super.origin(origin);
    }
    
    @Override
     public CopyOfBuilder region(String region) {
      return (CopyOfBuilder) super.region(region);
    }
    
    @Override
     public CopyOfBuilder altitudeLow(String altitudeLow) {
      return (CopyOfBuilder) super.altitudeLow(altitudeLow);
    }
    
    @Override
     public CopyOfBuilder altitudeHigh(String altitudeHigh) {
      return (CopyOfBuilder) super.altitudeHigh(altitudeHigh);
    }
    
    @Override
     public CopyOfBuilder moisture(String moisture) {
      return (CopyOfBuilder) super.moisture(moisture);
    }
    
    @Override
     public CopyOfBuilder variety(String variety) {
      return (CopyOfBuilder) super.variety(variety);
    }
    
    @Override
     public CopyOfBuilder density(String density) {
      return (CopyOfBuilder) super.density(density);
    }
    
    @Override
     public CopyOfBuilder grade(String grade) {
      return (CopyOfBuilder) super.grade(grade);
    }
    
    @Override
     public CopyOfBuilder image(String image) {
      return (CopyOfBuilder) super.image(image);
    }
    
    @Override
     public CopyOfBuilder dealer(String dealer) {
      return (CopyOfBuilder) super.dealer(dealer);
    }
  }
  
}
