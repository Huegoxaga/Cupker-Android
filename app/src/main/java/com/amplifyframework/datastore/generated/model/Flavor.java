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

/** This is an auto generated class representing the Flavor type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Flavors", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byBean", fields = {"beanID"})
public final class Flavor implements Model {
  public static final QueryField ID = field("Flavor", "id");
  public static final QueryField BEAN_ID = field("Flavor", "beanID");
  public static final QueryField NAME = field("Flavor", "name");
  public static final QueryField TYPE = field("Flavor", "type");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String beanID;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String type;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getBeanId() {
      return beanID;
  }
  
  public String getName() {
      return name;
  }
  
  public String getType() {
      return type;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Flavor(String id, String beanID, String name, String type) {
    this.id = id;
    this.beanID = beanID;
    this.name = name;
    this.type = type;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Flavor flavor = (Flavor) obj;
      return ObjectsCompat.equals(getId(), flavor.getId()) &&
              ObjectsCompat.equals(getBeanId(), flavor.getBeanId()) &&
              ObjectsCompat.equals(getName(), flavor.getName()) &&
              ObjectsCompat.equals(getType(), flavor.getType()) &&
              ObjectsCompat.equals(getCreatedAt(), flavor.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), flavor.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getBeanId())
      .append(getName())
      .append(getType())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Flavor {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("beanID=" + String.valueOf(getBeanId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("type=" + String.valueOf(getType()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BeanIdStep builder() {
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
  public static Flavor justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Flavor(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      beanID,
      name,
      type);
  }
  public interface BeanIdStep {
    BuildStep beanId(String beanId);
  }
  

  public interface BuildStep {
    Flavor build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep type(String type);
  }
  

  public static class Builder implements BeanIdStep, BuildStep {
    private String id;
    private String beanID;
    private String name;
    private String type;
    @Override
     public Flavor build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Flavor(
          id,
          beanID,
          name,
          type);
    }
    
    @Override
     public BuildStep beanId(String beanId) {
        Objects.requireNonNull(beanId);
        this.beanID = beanId;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep type(String type) {
        this.type = type;
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
    private CopyOfBuilder(String id, String beanId, String name, String type) {
      super.id(id);
      super.beanId(beanId)
        .name(name)
        .type(type);
    }
    
    @Override
     public CopyOfBuilder beanId(String beanId) {
      return (CopyOfBuilder) super.beanId(beanId);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder type(String type) {
      return (CopyOfBuilder) super.type(type);
    }
  }
  
}
