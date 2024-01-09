package br.com.agostinho.algafood.domain.model;

import br.com.agostinho.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COZINHA")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Builder
public class Cozinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
//    @JsonIgnore
    @NotNull(groups = Groups.CozinhaId.class)
    private Long id;

    @JsonProperty("titulo")
    @NotBlank
    private String nome;
}
