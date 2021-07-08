package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the Sample type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Samples", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "bySession", fields = {"sessionID"})
public final class Sample implements Model {
  public static final QueryField ID = field("Sample", "id");
  public static final QueryField SESSION_ID = field("Sample", "sessionID");
  public static final QueryField BEAN = field("Sample", "sampleBeanId");
  public static final QueryField AROMA = field("Sample", "aroma");
  public static final QueryField FLAVOR = field("Sample", "flavor");
  public static final QueryField ACIDITY = field("Sample", "acidity");
  public static final QueryField BODY = field("Sample", "body");
  public static final QueryField BALANCE = field("Sample", "balance");
  public static final QueryField UNIFORMITY = field("Sample", "uniformity");
  public static final QueryField CLEAN_CUP = field("Sample", "clean_cup");
  public static final QueryField AFTER_TASTE = field("Sample", "after_taste");
  public static final QueryField SWEETNESS = field("Sample", "sweetness");
  public static final QueryField DEFECTS = field("Sample", "defects");
  public static final QueryField DEFECT_TYPE = field("Sample", "defect_type");
  public static final QueryField DEFECT_COUNT = field("Sample", "defect_count");
  public static final QueryField NOTES = field("Sample", "notes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String sessionID;
  private final @ModelField(targetType="Bean", isRequired = true) @BelongsTo(targetName = "sampleBeanId", type = Bean.class) Bean bean;
  private final @ModelField(targetType="Float") Double aroma;
  private final @ModelField(targetType="Float") Double flavor;
  private final @ModelField(targetType="Float") Double acidity;
  private final @ModelField(targetType="Float") Double body;
  private final @ModelField(targetType="Float") Double balance;
  private final @ModelField(targetType="Float") Double uniformity;
  private final @ModelField(targetType="Float") Double clean_cup;
  private final @ModelField(targetType="Float") Double after_taste;
  private final @ModelField(targetType="Float") Double sweetness;
  private final @ModelField(targetType="Float") Double defects;
  private final @ModelField(targetType="String") String defect_type;
  private final @ModelField(targetType="Float") Double defect_count;
  private final @ModelField(targetType="String") String notes;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getSessionId() {
      return sessionID;
  }
  
  public Bean getBean() {
      return bean;
  }
  
  public Double getAroma() {
      return aroma;
  }
  
  public Double getFlavor() {
      return flavor;
  }
  
  public Double getAcidity() {
      return acidity;
  }
  
  public Double getBody() {
      return body;
  }
  
  public Double getBalance() {
      return balance;
  }
  
  public Double getUniformity() {
      return uniformity;
  }
  
  public Double getCleanCup() {
      return clean_cup;
  }
  
  public Double getAfterTaste() {
      return after_taste;
  }
  
  public Double getSweetness() {
      return sweetness;
  }
  
  public Double getDefects() {
      return defects;
  }
  
  public String getDefectType() {
      return defect_type;
  }
  
  public Double getDefectCount() {
      return defect_count;
  }
  
  public String getNotes() {
      return notes;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Sample(String id, String sessionID, Bean bean, Double aroma, Double flavor, Double acidity, Double body, Double balance, Double uniformity, Double clean_cup, Double after_taste, Double sweetness, Double defects, String defect_type, Double defect_count, String notes) {
    this.id = id;
    this.sessionID = sessionID;
    this.bean = bean;
    this.aroma = aroma;
    this.flavor = flavor;
    this.acidity = acidity;
    this.body = body;
    this.balance = balance;
    this.uniformity = uniformity;
    this.clean_cup = clean_cup;
    this.after_taste = after_taste;
    this.sweetness = sweetness;
    this.defects = defects;
    this.defect_type = defect_type;
    this.defect_count = defect_count;
    this.notes = notes;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Sample sample = (Sample) obj;
      return ObjectsCompat.equals(getId(), sample.getId()) &&
              ObjectsCompat.equals(getSessionId(), sample.getSessionId()) &&
              ObjectsCompat.equals(getBean(), sample.getBean()) &&
              ObjectsCompat.equals(getAroma(), sample.getAroma()) &&
              ObjectsCompat.equals(getFlavor(), sample.getFlavor()) &&
              ObjectsCompat.equals(getAcidity(), sample.getAcidity()) &&
              ObjectsCompat.equals(getBody(), sample.getBody()) &&
              ObjectsCompat.equals(getBalance(), sample.getBalance()) &&
              ObjectsCompat.equals(getUniformity(), sample.getUniformity()) &&
              ObjectsCompat.equals(getCleanCup(), sample.getCleanCup()) &&
              ObjectsCompat.equals(getAfterTaste(), sample.getAfterTaste()) &&
              ObjectsCompat.equals(getSweetness(), sample.getSweetness()) &&
              ObjectsCompat.equals(getDefects(), sample.getDefects()) &&
              ObjectsCompat.equals(getDefectType(), sample.getDefectType()) &&
              ObjectsCompat.equals(getDefectCount(), sample.getDefectCount()) &&
              ObjectsCompat.equals(getNotes(), sample.getNotes()) &&
              ObjectsCompat.equals(getCreatedAt(), sample.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), sample.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getSessionId())
      .append(getBean())
      .append(getAroma())
      .append(getFlavor())
      .append(getAcidity())
      .append(getBody())
      .append(getBalance())
      .append(getUniformity())
      .append(getCleanCup())
      .append(getAfterTaste())
      .append(getSweetness())
      .append(getDefects())
      .append(getDefectType())
      .append(getDefectCount())
      .append(getNotes())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Sample {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("sessionID=" + String.valueOf(getSessionId()) + ", ")
      .append("bean=" + String.valueOf(getBean()) + ", ")
      .append("aroma=" + String.valueOf(getAroma()) + ", ")
      .append("flavor=" + String.valueOf(getFlavor()) + ", ")
      .append("acidity=" + String.valueOf(getAcidity()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("balance=" + String.valueOf(getBalance()) + ", ")
      .append("uniformity=" + String.valueOf(getUniformity()) + ", ")
      .append("clean_cup=" + String.valueOf(getCleanCup()) + ", ")
      .append("after_taste=" + String.valueOf(getAfterTaste()) + ", ")
      .append("sweetness=" + String.valueOf(getSweetness()) + ", ")
      .append("defects=" + String.valueOf(getDefects()) + ", ")
      .append("defect_type=" + String.valueOf(getDefectType()) + ", ")
      .append("defect_count=" + String.valueOf(getDefectCount()) + ", ")
      .append("notes=" + String.valueOf(getNotes()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static SessionIdStep builder() {
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
  public static Sample justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Sample(
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
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      sessionID,
      bean,
      aroma,
      flavor,
      acidity,
      body,
      balance,
      uniformity,
      clean_cup,
      after_taste,
      sweetness,
      defects,
      defect_type,
      defect_count,
      notes);
  }
  public interface SessionIdStep {
    BeanStep sessionId(String sessionId);
  }
  

  public interface BeanStep {
    BuildStep bean(Bean bean);
  }
  

  public interface BuildStep {
    Sample build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep aroma(Double aroma);
    BuildStep flavor(Double flavor);
    BuildStep acidity(Double acidity);
    BuildStep body(Double body);
    BuildStep balance(Double balance);
    BuildStep uniformity(Double uniformity);
    BuildStep cleanCup(Double cleanCup);
    BuildStep afterTaste(Double afterTaste);
    BuildStep sweetness(Double sweetness);
    BuildStep defects(Double defects);
    BuildStep defectType(String defectType);
    BuildStep defectCount(Double defectCount);
    BuildStep notes(String notes);
  }
  

  public static class Builder implements SessionIdStep, BeanStep, BuildStep {
    private String id;
    private String sessionID;
    private Bean bean;
    private Double aroma;
    private Double flavor;
    private Double acidity;
    private Double body;
    private Double balance;
    private Double uniformity;
    private Double clean_cup;
    private Double after_taste;
    private Double sweetness;
    private Double defects;
    private String defect_type;
    private Double defect_count;
    private String notes;
    @Override
     public Sample build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Sample(
          id,
          sessionID,
          bean,
          aroma,
          flavor,
          acidity,
          body,
          balance,
          uniformity,
          clean_cup,
          after_taste,
          sweetness,
          defects,
          defect_type,
          defect_count,
          notes);
    }
    
    @Override
     public BeanStep sessionId(String sessionId) {
        Objects.requireNonNull(sessionId);
        this.sessionID = sessionId;
        return this;
    }
    
    @Override
     public BuildStep bean(Bean bean) {
        Objects.requireNonNull(bean);
        this.bean = bean;
        return this;
    }
    
    @Override
     public BuildStep aroma(Double aroma) {
        this.aroma = aroma;
        return this;
    }
    
    @Override
     public BuildStep flavor(Double flavor) {
        this.flavor = flavor;
        return this;
    }
    
    @Override
     public BuildStep acidity(Double acidity) {
        this.acidity = acidity;
        return this;
    }
    
    @Override
     public BuildStep body(Double body) {
        this.body = body;
        return this;
    }
    
    @Override
     public BuildStep balance(Double balance) {
        this.balance = balance;
        return this;
    }
    
    @Override
     public BuildStep uniformity(Double uniformity) {
        this.uniformity = uniformity;
        return this;
    }
    
    @Override
     public BuildStep cleanCup(Double cleanCup) {
        this.clean_cup = cleanCup;
        return this;
    }
    
    @Override
     public BuildStep afterTaste(Double afterTaste) {
        this.after_taste = afterTaste;
        return this;
    }
    
    @Override
     public BuildStep sweetness(Double sweetness) {
        this.sweetness = sweetness;
        return this;
    }
    
    @Override
     public BuildStep defects(Double defects) {
        this.defects = defects;
        return this;
    }
    
    @Override
     public BuildStep defectType(String defectType) {
        this.defect_type = defectType;
        return this;
    }
    
    @Override
     public BuildStep defectCount(Double defectCount) {
        this.defect_count = defectCount;
        return this;
    }
    
    @Override
     public BuildStep notes(String notes) {
        this.notes = notes;
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
    private CopyOfBuilder(String id, String sessionId, Bean bean, Double aroma, Double flavor, Double acidity, Double body, Double balance, Double uniformity, Double cleanCup, Double afterTaste, Double sweetness, Double defects, String defectType, Double defectCount, String notes) {
      super.id(id);
      super.sessionId(sessionId)
        .bean(bean)
        .aroma(aroma)
        .flavor(flavor)
        .acidity(acidity)
        .body(body)
        .balance(balance)
        .uniformity(uniformity)
        .cleanCup(cleanCup)
        .afterTaste(afterTaste)
        .sweetness(sweetness)
        .defects(defects)
        .defectType(defectType)
        .defectCount(defectCount)
        .notes(notes);
    }
    
    @Override
     public CopyOfBuilder sessionId(String sessionId) {
      return (CopyOfBuilder) super.sessionId(sessionId);
    }
    
    @Override
     public CopyOfBuilder bean(Bean bean) {
      return (CopyOfBuilder) super.bean(bean);
    }
    
    @Override
     public CopyOfBuilder aroma(Double aroma) {
      return (CopyOfBuilder) super.aroma(aroma);
    }
    
    @Override
     public CopyOfBuilder flavor(Double flavor) {
      return (CopyOfBuilder) super.flavor(flavor);
    }
    
    @Override
     public CopyOfBuilder acidity(Double acidity) {
      return (CopyOfBuilder) super.acidity(acidity);
    }
    
    @Override
     public CopyOfBuilder body(Double body) {
      return (CopyOfBuilder) super.body(body);
    }
    
    @Override
     public CopyOfBuilder balance(Double balance) {
      return (CopyOfBuilder) super.balance(balance);
    }
    
    @Override
     public CopyOfBuilder uniformity(Double uniformity) {
      return (CopyOfBuilder) super.uniformity(uniformity);
    }
    
    @Override
     public CopyOfBuilder cleanCup(Double cleanCup) {
      return (CopyOfBuilder) super.cleanCup(cleanCup);
    }
    
    @Override
     public CopyOfBuilder afterTaste(Double afterTaste) {
      return (CopyOfBuilder) super.afterTaste(afterTaste);
    }
    
    @Override
     public CopyOfBuilder sweetness(Double sweetness) {
      return (CopyOfBuilder) super.sweetness(sweetness);
    }
    
    @Override
     public CopyOfBuilder defects(Double defects) {
      return (CopyOfBuilder) super.defects(defects);
    }
    
    @Override
     public CopyOfBuilder defectType(String defectType) {
      return (CopyOfBuilder) super.defectType(defectType);
    }
    
    @Override
     public CopyOfBuilder defectCount(Double defectCount) {
      return (CopyOfBuilder) super.defectCount(defectCount);
    }
    
    @Override
     public CopyOfBuilder notes(String notes) {
      return (CopyOfBuilder) super.notes(notes);
    }
  }
  
}
