package com.example.shop.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="product")
public class ProductEntity extends BaseEntity {
    private String name;

    private boolean forSale;

    private boolean isAvailable;
    
    @ManyToMany(
        fetch = FetchType.LAZY, 
        cascade = {
            CascadeType.MERGE, 
            CascadeType.REFRESH})
    @JoinTable(
        name = "product_subscriber", 
        joinColumns = @JoinColumn(name = "product_id"), 
        inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    private Set<SubscriberEntity> subscribers;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (forSale ? 1231 : 1237);
        result = prime * result + (isAvailable ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProductEntity other = (ProductEntity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (forSale != other.forSale)
            return false;
        if (isAvailable != other.isAvailable)
            return false;
        return true;
    }

    
}
