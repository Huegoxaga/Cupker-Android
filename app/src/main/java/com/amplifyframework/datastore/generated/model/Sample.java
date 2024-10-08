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

/** This is an auto generated class representing the Sample type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Samples", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "bySession", fields = {"sessionID"})
@Index(name = "byBean", fields = {"beanID"})
public final class Sample implements Model {
  public static final QueryField ID = field("Sample", "id");
  public static final QueryField SESSION_ID = field("Sample", "sessionID");
  public static final QueryField SAMPLE_ORDER = field("Sample", "sampleOrder");
  public static final QueryField BEAN_ID = field("Sample", "beanID");
  public static final QueryField ROAST_LEVEL = field("Sample", "roast_level");
  public static final QueryField AROMA = field("Sample", "aroma");
  public static final QueryField FLAVOR = field("Sample", "flavor");
  public static final QueryField ACIDITY = field("Sample", "acidity");
  public static final QueryField OVERALL = field("Sample", "overall");
  public static final QueryField BODY = field("Sample", "body");
  public static final QueryField BALANCE = field("Sample", "balance");
  public static final QueryField UNIFORMITY = field("Sample", "uniformity");
  public static final QueryField CLEAN_CUP = field("Sample", "clean_cup");
  public static final QueryField AFTER_TASTE = field("Sample", "after_taste");
  public static final QueryField SWEETNESS = field("Sample", "sweetness");
  public static final QueryField DEFECT_TYPE = field("Sample", "defect_type");
  public static final QueryField DEFECT_COUNT = field("Sample", "defect_count");
  public static final QueryField NOTES = field("Sample", "notes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String sessionID;
  private final @ModelField(targetType="Int") Integer sampleOrder;
  private final @ModelField(targetType="ID") String beanID;
  private final @ModelField(targetType="Float") Double roast_level;
  private final @ModelField(targetType="Float") Double aroma;
  private final @ModelField(targetType="Float") Double flavor;
  private final @ModelField(targetType="Float") Double acidity;
  private final @ModelField(targetType="Float") Double overall;
  private final @ModelField(targetType="Float") Double body;
  private final @ModelField(targetType="Float") Double balance;
  private final @ModelField(targetType="Float") Double uniformity;
  private final @ModelField(targetType="Float") Double clean_cup;
  private final @ModelField(targetType="Float") Double after_taste;
  private final @ModelField(targetType="Float") Double sweetness;
  private final @ModelField(targetType="Float") Double defect_type;
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
  
  public Integer getSampleOrder() {
      return sampleOrder;
  }
  
  public String getBeanId() {
      return beanID;
  }
  
  public Double getRoastLevel() {
      return roast_level;
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
  
  public Double getOverall() {
      return overall;
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
  
  public Double getDefectType() {
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
  
  private Sample(String id, String sessionID, Integer sampleOrder, String beanID, Double roast_level, Double aroma, Double flavor, Double acidity, Double overall, Double body, Double balance, Double uniformity, Double clean_cup, Double after_taste, Double sweetness, Double defect_type, Double defect_count, String notes) {
    this.id = id;
    this.sessionID = sessionID;
    this.sampleOrder = sampleOrder;
    this.beanID = beanID;
    this.roast_level = roast_level;
    this.aroma = aroma;
    this.flavor = flavor;
    this.acidity = acidity;
    this.overall = overall;
    this.body = body;
    this.balance = balance;
    this.uniformity = uniformity;
    this.clean_cup = clean_cup;
    this.after_taste = after_taste;
    this.sweetness = sweetness;
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
              ObjectsCompat.equals(getSampleOrder(), sample.getSampleOrder()) &&
              ObjectsCompat.equals(getBeanId(), sample.getBeanId()) &&
              ObjectsCompat.equals(getRoastLevel(), sample.getRoastLevel()) &&
              ObjectsCompat.equals(getAroma(), sample.getAroma()) &&
              ObjectsCompat.equals(getFlavor(), sample.getFlavor()) &&
              ObjectsCompat.equals(getAcidity(), sample.getAcidity()) &&
              ObjectsCompat.equals(getOverall(), sample.getOverall()) &&
              ObjectsCompat.equals(getBody(), sample.getBody()) &&
              ObjectsCompat.equals(getBalance(), sample.getBalance()) &&
              ObjectsCompat.equals(getUniformity(), sample.getUniformity()) &&
              ObjectsCompat.equals(getCleanCup(), sample.getCleanCup()) &&
              ObjectsCompat.equals(getAfterTaste(), sample.getAfterTaste()) &&
              ObjectsCompat.equals(getSweetness(), sample.getSweetness()) &&
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
      .append(getSampleOrder())
      .append(getBeanId())
      .append(getRoastLevel())
      .append(getAroma())
      .append(getFlavor())
      .append(getAcidity())
      .append(getOverall())
      .append(getBody())
      .append(getBalance())
      .append(getUniformity())
      .append(getCleanCup())
      .append(getAfterTaste())
      .append(getSweetness())
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
      .append("sampleOrder=" + String.valueOf(getSampleOrder()) + ", ")
      .append("beanID=" + String.valueOf(getBeanId()) + ", ")
      .append("roast_level=" + String.valueOf(getRoastLevel()) + ", ")
      .append("aroma=" + String.valueOf(getAroma()) + ", ")
      .append("flavor=" + String.valueOf(getFlavor()) + ", ")
      .append("acidity=" + String.valueOf(getAcidity()) + ", ")
      .append("overall=" + String.valueOf(getOverall()) + ", ")
      .append("body=" + String.valueOf(getBody()) + ", ")
      .append("balance=" + String.valueOf(getBalance()) + ", ")
      .append("uniformity=" + String.valueOf(getUniformity()) + ", ")
      .append("clean_cup=" + String.valueOf(getCleanCup()) + ", ")
      .append("after_taste=" + String.valueOf(getAfterTaste()) + ", ")
      .append("sweetness=" + String.valueOf(getSweetness()) + ", ")
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
   */
  public static Sample justId(String id) {
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
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      sessionID,
      sampleOrder,
      beanID,
      roast_level,
      aroma,
      flavor,
      acidity,
      overall,
      body,
      balance,
      uniformity,
      clean_cup,
      after_taste,
      sweetness,
      defect_type,
      defect_count,
      notes);
  }
  public interface SessionIdStep {
    BuildStep sessionId(String sessionId);
  }
  

  public interface BuildStep {
    Sample build();
    BuildStep id(String id);
    BuildStep sampleOrder(Integer sampleOrder);
    BuildStep beanId(String beanId);
    BuildStep roastLevel(Double roastLevel);
    BuildStep aroma(Double aroma);
    BuildStep flavor(Double flavor);
    BuildStep acidity(Double acidity);
    BuildStep overall(Double overall);
    BuildStep body(Double body);
    BuildStep balance(Double balance);
    BuildStep uniformity(Double uniformity);
    BuildStep cleanCup(Double cleanCup);
    BuildStep afterTaste(Double afterTaste);
    BuildStep sweetness(Double sweetness);
    BuildStep defectType(Double defectType);
    BuildStep defectCount(Double defectCount);
    BuildStep notes(String notes);
  }
  

  public static class Builder implements SessionIdStep, BuildStep {
    private String id;
    private String sessionID;
    private Integer sampleOrder;
    private String beanID;
    private Double roast_level;
    private Double aroma;
    private Double flavor;
    private Double acidity;
    private Double overall;
    private Double body;
    private Double balance;
    private Double uniformity;
    private Double clean_cup;
    private Double after_taste;
    private Double sweetness;
    private Double defect_type;
    private Double defect_count;
    private String notes;
    @Override
     public Sample build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Sample(
          id,
          sessionID,
          sampleOrder,
          beanID,
          roast_level,
          aroma,
          flavor,
          acidity,
          overall,
          body,
          balance,
          uniformity,
          clean_cup,
          after_taste,
          sweetness,
          defect_type,
          defect_count,
          notes);
    }
    
    @Override
     public BuildStep sessionId(String sessionId) {
        Objects.requireNonNull(sessionId);
        this.sessionID = sessionId;
        return this;
    }
    
    @Override
     public BuildStep sampleOrder(Integer sampleOrder) {
        this.sampleOrder = sampleOrder;
        return this;
    }
    
    @Override
     public BuildStep beanId(String beanId) {
        this.beanID = beanId;
        return this;
    }
    
    @Override
     public BuildStep roastLevel(Double roastLevel) {
        this.roast_level = roastLevel;
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
     public BuildStep overall(Double overall) {
        this.overall = overall;
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
     public BuildStep defectType(Double defectType) {
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
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String sessionId, Integer sampleOrder, String beanId, Double roastLevel, Double aroma, Double flavor, Double acidity, Double overall, Double body, Double balance, Double uniformity, Double cleanCup, Double afterTaste, Double sweetness, Double defectType, Double defectCount, String notes) {
      super.id(id);
      super.sessionId(sessionId)
        .sampleOrder(sampleOrder)
        .beanId(beanId)
        .roastLevel(roastLevel)
        .aroma(aroma)
        .flavor(flavor)
        .acidity(acidity)
        .overall(overall)
        .body(body)
        .balance(balance)
        .uniformity(uniformity)
        .cleanCup(cleanCup)
        .afterTaste(afterTaste)
        .sweetness(sweetness)
        .defectType(defectType)
        .defectCount(defectCount)
        .notes(notes);
    }
    
    @Override
     public CopyOfBuilder sessionId(String sessionId) {
      return (CopyOfBuilder) super.sessionId(sessionId);
    }
    
    @Override
     public CopyOfBuilder sampleOrder(Integer sampleOrder) {
      return (CopyOfBuilder) super.sampleOrder(sampleOrder);
    }
    
    @Override
     public CopyOfBuilder beanId(String beanId) {
      return (CopyOfBuilder) super.beanId(beanId);
    }
    
    @Override
     public CopyOfBuilder roastLevel(Double roastLevel) {
      return (CopyOfBuilder) super.roastLevel(roastLevel);
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
     public CopyOfBuilder overall(Double overall) {
      return (CopyOfBuilder) super.overall(overall);
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
     public CopyOfBuilder defectType(Double defectType) {
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
