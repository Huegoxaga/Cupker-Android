package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;

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

/** This is an auto generated class representing the Session type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Sessions")
public final class Session implements Model {
  public static final QueryField ID = field("Session", "id");
  public static final QueryField USER = field("Session", "sessionUserId");
  public static final QueryField ROASTER = field("Session", "sessionRoasterId");
  public static final QueryField ROAST_TIME = field("Session", "roast_time");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="User", isRequired = true) @BelongsTo(targetName = "sessionUserId", type = User.class) User user;
  private final @ModelField(targetType="Roaster", isRequired = true) @BelongsTo(targetName = "sessionRoasterId", type = Roaster.class) Roaster roaster;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime roast_time;
  public String getId() {
      return id;
  }
  
  public User getUser() {
      return user;
  }
  
  public Roaster getRoaster() {
      return roaster;
  }
  
  public Temporal.DateTime getRoastTime() {
      return roast_time;
  }
  
  private Session(String id, User user, Roaster roaster, Temporal.DateTime roast_time) {
    this.id = id;
    this.user = user;
    this.roaster = roaster;
    this.roast_time = roast_time;
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
              ObjectsCompat.equals(getUser(), session.getUser()) &&
              ObjectsCompat.equals(getRoaster(), session.getRoaster()) &&
              ObjectsCompat.equals(getRoastTime(), session.getRoastTime());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getRoaster())
      .append(getRoastTime())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Session {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("roaster=" + String.valueOf(getRoaster()) + ", ")
      .append("roast_time=" + String.valueOf(getRoastTime()))
      .append("}")
      .toString();
  }
  
  public static UserStep builder() {
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
  public static Session justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Session(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      roaster,
      roast_time);
  }
  public interface UserStep {
    RoasterStep user(User user);
  }
  

  public interface RoasterStep {
    RoastTimeStep roaster(Roaster roaster);
  }
  

  public interface RoastTimeStep {
    BuildStep roastTime(Temporal.DateTime roastTime);
  }
  

  public interface BuildStep {
    Session build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements UserStep, RoasterStep, RoastTimeStep, BuildStep {
    private String id;
    private User user;
    private Roaster roaster;
    private Temporal.DateTime roast_time;
    @Override
     public Session build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Session(
          id,
          user,
          roaster,
          roast_time);
    }
    
    @Override
     public RoasterStep user(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        return this;
    }
    
    @Override
     public RoastTimeStep roaster(Roaster roaster) {
        Objects.requireNonNull(roaster);
        this.roaster = roaster;
        return this;
    }
    
    @Override
     public BuildStep roastTime(Temporal.DateTime roastTime) {
        Objects.requireNonNull(roastTime);
        this.roast_time = roastTime;
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
    private CopyOfBuilder(String id, User user, Roaster roaster, Temporal.DateTime roastTime) {
      super.id(id);
      super.user(user)
        .roaster(roaster)
        .roastTime(roastTime);
    }
    
    @Override
     public CopyOfBuilder user(User user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder roaster(Roaster roaster) {
      return (CopyOfBuilder) super.roaster(roaster);
    }
    
    @Override
     public CopyOfBuilder roastTime(Temporal.DateTime roastTime) {
      return (CopyOfBuilder) super.roastTime(roastTime);
    }
  }
  
}
