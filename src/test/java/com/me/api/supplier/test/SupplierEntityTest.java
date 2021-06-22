package com.me.api.supplier.test;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.me.api.supplier.domain.SupplierEntity;
import com.me.api.supplier.repository.SupplierRepository;
import com.me.api.supplier.service.AsciiArtService;
import com.me.api.supplier.service.identifier.FiscalIdService;
import com.me.api.supplier.service.identifier.SupplierIdService;

import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@DataR2dbcTest
public class SupplierEntityTest {

	@Autowired private SupplierRepository supplierRepository;
	@Autowired private AsciiArtService asciiArtService;
	@Autowired private SupplierIdService supplierIdService;
	@Autowired private FiscalIdService fiscalIdService;
	
	@BeforeEach
	public void setup() {
		
		asciiArtService.display("SETUP");
		
		supplierRepository.deleteAll().block();
		
		supplierRepository.save(new SupplierEntity(null, supplierIdService.getId(), 
				"DEXTER", "001", fiscalIdService.getId(), 
				LocalDateTime.now(), null)).
				log().
				block();
	}
	
	@Test
	public void findManySuppliers() {
		
		asciiArtService.display("FIND MANY SUPPLIERS");
		
		StepVerifier.create(supplierRepository.findAll().log()).
			expectNextMatches(supplier -> supplier.getName().equals("DEXTER")).
			verifyComplete();
	}
}
