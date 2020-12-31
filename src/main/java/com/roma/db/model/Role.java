package com.roma.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;


public enum Role {
    USER, ADMIN, MAID;
}
