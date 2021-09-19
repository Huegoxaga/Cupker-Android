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

/** This is an auto generated class representing the Roaster type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Roasters", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Roaster implements Model {
  public static final QueryField ID = field("Roaster", "id");
  public static final QueryField NAME = field("Roaster", "name");
  public static final QueryField EMAIL = field("Roaster", "email");
  public static final QueryField STATUS = field("Roaster", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="AWSEmail") String email;
  private final @ModelField(targetType="Status", isRequired = true) Status status;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getEmail() {
      return email;
  }
  
  public Status getStatus() {
      return status;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Roaster(String id, String name, String email, Status status) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Roaster roaster = (Roaster) obj;
      return ObjectsCompat.equals(getId(), roaster.getId()) &&
              ObjectsCompat.equals(getName(), roaster.getName()) &&
              ObjectsCompat.equals(getEmail(), roaster.getEmail()) &&
              ObjectsCompat.equals(getStatus(), roaster.getStatus()) &&
              ObjectsCompat.equals(getCreatedAt(), roaster.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), roaster.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getEmail())
      .append(getStatus())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Roaster {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
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
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Roaster justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Roaster(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      email,
      status);
  }
  public interface NameStep {
    StatusStep name(String name);
  }
  

  public interface StatusStep {
    BuildStep status(Status status);
  }
  

  public interface BuildStep {
    Roaster build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep email(String email);
  }
  

  public static class Builder implements NameStep, StatusStep, BuildStep {
    private String id;
    private String name;
    private Status status;
    private String email;
    @Override
     public Roaster build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Roaster(
          id,
          name,
          email,
          status);
    }
    
    @Override
     public StatusStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep status(Status status) {
        Objects.requireNonNull(status);
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        this.email = email;
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
    private CopyOfBuilder(String id, String name, String email, Status status) {
      super.id(id);
      super.name(name)
        .status(status)
        .email(email);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder status(Status status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
  }
  
}
