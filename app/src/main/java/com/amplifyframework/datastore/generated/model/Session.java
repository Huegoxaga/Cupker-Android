package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasMany;

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

/** This is an auto generated class representing the Session type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Sessions", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byRoaster", fields = {"roasterID","roast_time"})
public final class Session implements Model {
  public static final QueryField ID = field("Session", "id");
  public static final QueryField NAME = field("Session", "name");
  public static final QueryField ROASTER_ID = field("Session", "roasterID");
  public static final QueryField ROAST_TIME = field("Session", "roast_time");
  public static final QueryField STATUS = field("Session", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="ID", isRequired = true) String roasterID;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime roast_time;
  private final @ModelField(targetType="Status", isRequired = true) Status status;
  private final @ModelField(targetType="Sample") @HasMany(associatedWith = "sessionID", type = Sample.class) List<Sample> sample = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getRoasterId() {
      return roasterID;
  }
  
  public Temporal.DateTime getRoastTime() {
      return roast_time;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public List<Sample> getSample() {
      return sample;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Session(String id, String name, String roasterID, Temporal.DateTime roast_time, Status status) {
    this.id = id;
    this.name = name;
    this.roasterID = roasterID;
    this.roast_time = roast_time;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Session session = (Session) obj;
      return ObjectsCompat.equals(getId(), session.getId()) &&
              ObjectsCompat.equals(getName(), session.getName()) &&
              ObjectsCompat.equals(getRoasterId(), session.getRoasterId()) &&
              ObjectsCompat.equals(getRoastTime(), session.getRoastTime()) &&
              ObjectsCompat.equals(getStatus(), session.getStatus()) &&
              ObjectsCompat.equals(getCreatedAt(), session.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), session.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getRoasterId())
      .append(getRoastTime())
      .append(getStatus())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Session {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("roasterID=" + String.valueOf(getRoasterId()) + ", ")
      .append("roast_time=" + String.valueOf(getRoastTime()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Session justId(String id) {
    return new Session(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      roasterID,
      roast_time,
      status);
  }
  public interface NameStep {
    RoasterIdStep name(String name);
  }
  

  public interface RoasterIdStep {
    RoastTimeStep roasterId(String roasterId);
  }
  

  public interface RoastTimeStep {
    StatusStep roastTime(Temporal.DateTime roastTime);
  }
  

  public interface StatusStep {
    BuildStep status(Status status);
  }
  

  public interface BuildStep {
    Session build();
    BuildStep id(String id);
  }
  

  public static class Builder implements NameStep, RoasterIdStep, RoastTimeStep, StatusStep, BuildStep {
    private String id;
    private String name;
    private String roasterID;
    private Temporal.DateTime roast_time;
    private Status status;
    @Override
     public Session build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Session(
          id,
          name,
          roasterID,
          roast_time,
          status);
    }
    
    @Override
     public RoasterIdStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public RoastTimeStep roasterId(String roasterId) {
        Objects.requireNonNull(roasterId);
        this.roasterID = roasterId;
        return this;
    }
    
    @Override
     public StatusStep roastTime(Temporal.DateTime roastTime) {
        Objects.requireNonNull(roastTime);
        this.roast_time = roastTime;
        return this;
    }
    
    @Override
     public BuildStep status(Status status) {
        Objects.requireNonNull(status);
        this.status = status;
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
    private CopyOfBuilder(String id, String name, String roasterId, Temporal.DateTime roastTime, Status status) {
      super.id(id);
      super.name(name)
        .roasterId(roasterId)
        .roastTime(roastTime)
        .status(status);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder roasterId(String roasterId) {
      return (CopyOfBuilder) super.roasterId(roasterId);
    }
    
    @Override
     public CopyOfBuilder roastTime(Temporal.DateTime roastTime) {
      return (CopyOfBuilder) super.roastTime(roastTime);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
  }
  
}
