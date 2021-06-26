package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Bean type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Beans")
public final class Bean implements Model {
  public static final QueryField ID = field("Bean", "id");
  public static final QueryField NAME = field("Bean", "name");
  public static final QueryField PROCESS = field("Bean", "process");
  public static final QueryField ORIGIN = field("Bean", "origin");
  public static final QueryField REGION = field("Bean", "region");
  public static final QueryField ALTITUDE_LOW = field("Bean", "altitude_low");
  public static final QueryField ALTITUDE_HIGH = field("Bean", "altitude_high");
  public static final QueryField MOISTURE = field("Bean", "moisture");
  public static final QueryField VARIATY = field("Bean", "variaty");
  public static final QueryField DENSITY = field("Bean", "density");
  public static final QueryField GRADE = field("Bean", "grade");
  public static final QueryField IMAGE_URL = field("Bean", "image_url");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String process;
  private final @ModelField(targetType="String") String origin;
  private final @ModelField(targetType="String") String region;
  private final @ModelField(targetType="String") String altitude_low;
  private final @ModelField(targetType="String") String altitude_high;
  private final @ModelField(targetType="String") String moisture;
  private final @ModelField(targetType="String") String variaty;
  private final @ModelField(targetType="String") String density;
  private final @ModelField(targetType="String") String grade;
  private final @ModelField(targetType="String") String image_url;
  public String getId() {
      return id;
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
  
  public String getVariaty() {
      return variaty;
  }
  
  public String getDensity() {
      return density;
  }
  
  public String getGrade() {
      return grade;
  }
  
  public String getImageUrl() {
      return image_url;
  }
  
  private Bean(String id, String name, String process, String origin, String region, String altitude_low, String altitude_high, String moisture, String variaty, String density, String grade, String image_url) {
    this.id = id;
    this.name = name;
    this.process = process;
    this.origin = origin;
    this.region = region;
    this.altitude_low = altitude_low;
    this.altitude_high = altitude_high;
    this.moisture = moisture;
    this.variaty = variaty;
    this.density = density;
    this.grade = grade;
    this.image_url = image_url;
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
              ObjectsCompat.equals(getName(), bean.getName()) &&
              ObjectsCompat.equals(getProcess(), bean.getProcess()) &&
              ObjectsCompat.equals(getOrigin(), bean.getOrigin()) &&
              ObjectsCompat.equals(getRegion(), bean.getRegion()) &&
              ObjectsCompat.equals(getAltitudeLow(), bean.getAltitudeLow()) &&
              ObjectsCompat.equals(getAltitudeHigh(), bean.getAltitudeHigh()) &&
              ObjectsCompat.equals(getMoisture(), bean.getMoisture()) &&
              ObjectsCompat.equals(getVariaty(), bean.getVariaty()) &&
              ObjectsCompat.equals(getDensity(), bean.getDensity()) &&
              ObjectsCompat.equals(getGrade(), bean.getGrade()) &&
              ObjectsCompat.equals(getImageUrl(), bean.getImageUrl());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getProcess())
      .append(getOrigin())
      .append(getRegion())
      .append(getAltitudeLow())
      .append(getAltitudeHigh())
      .append(getMoisture())
      .append(getVariaty())
      .append(getDensity())
      .append(getGrade())
      .append(getImageUrl())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Bean {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("process=" + String.valueOf(getProcess()) + ", ")
      .append("origin=" + String.valueOf(getOrigin()) + ", ")
      .append("region=" + String.valueOf(getRegion()) + ", ")
      .append("altitude_low=" + String.valueOf(getAltitudeLow()) + ", ")
      .append("altitude_high=" + String.valueOf(getAltitudeHigh()) + ", ")
      .append("moisture=" + String.valueOf(getMoisture()) + ", ")
      .append("variaty=" + String.valueOf(getVariaty()) + ", ")
      .append("density=" + String.valueOf(getDensity()) + ", ")
      .append("grade=" + String.valueOf(getGrade()) + ", ")
      .append("image_url=" + String.valueOf(getImageUrl()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Bean justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      process,
      origin,
      region,
      altitude_low,
      altitude_high,
      moisture,
      variaty,
      density,
      grade,
      image_url);
  }
  public interface BuildStep {
    Bean build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep process(String process);
    BuildStep origin(String origin);
    BuildStep region(String region);
    BuildStep altitudeLow(String altitudeLow);
    BuildStep altitudeHigh(String altitudeHigh);
    BuildStep moisture(String moisture);
    BuildStep variaty(String variaty);
    BuildStep density(String density);
    BuildStep grade(String grade);
    BuildStep imageUrl(String imageUrl);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String process;
    private String origin;
    private String region;
    private String altitude_low;
    private String altitude_high;
    private String moisture;
    private String variaty;
    private String density;
    private String grade;
    private String image_url;
    @Override
     public Bean build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Bean(
          id,
          name,
          process,
          origin,
          region,
          altitude_low,
          altitude_high,
          moisture,
          variaty,
          density,
          grade,
          image_url);
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
     public BuildStep variaty(String variaty) {
        this.variaty = variaty;
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
     public BuildStep imageUrl(String imageUrl) {
        this.image_url = imageUrl;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String process, String origin, String region, String altitudeLow, String altitudeHigh, String moisture, String variaty, String density, String grade, String imageUrl) {
      super.id(id);
      super.name(name)
        .process(process)
        .origin(origin)
        .region(region)
        .altitudeLow(altitudeLow)
        .altitudeHigh(altitudeHigh)
        .moisture(moisture)
        .variaty(variaty)
        .density(density)
        .grade(grade)
        .imageUrl(imageUrl);
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
     public CopyOfBuilder variaty(String variaty) {
      return (CopyOfBuilder) super.variaty(variaty);
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
     public CopyOfBuilder imageUrl(String imageUrl) {
      return (CopyOfBuilder) super.imageUrl(imageUrl);
    }
  }
  
}
