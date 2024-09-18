package com.nnk.springboot.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class CurvePointServiceTests {
	
	@Mock
	private CurvePointRepository curvePointRepository;
	
	@InjectMocks
	private CurvePointService curvePointService;
	
	@Test
	public void testGetCurvePointById() {
		CurvePoint curvePoint = new CurvePoint(1, 10, 1.0, 1.0);
		
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(curvePoint));
		
		Optional<CurvePoint> result = curvePointService.getCurvePointById(1);
		
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
	}
	
	@Test
	public void testSaveCurvePoint() {
		CurvePoint curvePoint = new CurvePoint(10, 1.0, 1.0);
		when(curvePointRepository.save(curvePoint)).thenReturn(curvePoint);
		
		CurvePoint savedCurvePoint = curvePointService.saveCurvePoint(curvePoint);
		
		assertNotNull(savedCurvePoint);
		assertEquals(10, savedCurvePoint.getCurve_id());
	}
	
	@Test
	public void testUpdateCurvePoint() {
		CurvePoint existingCurvePoint = new CurvePoint(1, 10, 1.0, 1.0);
		CurvePoint updatedCurvePoint = new CurvePoint(20, 2.0, 2.0);
		
		when(curvePointRepository.findById(1)).thenReturn(Optional.of(existingCurvePoint));
		when(curvePointRepository.save(existingCurvePoint)).thenReturn(existingCurvePoint);
		
		CurvePoint result = curvePointService.updateCurvePoint(1, updatedCurvePoint);
		assertEquals(20, result.getCurve_id());
		assertEquals(2.0, result.getTerm());
		assertEquals(2.0, result.getValue());
	}
	
	@Test
	public void testDeleteCurvePoint_NotFound() {
		when(curvePointRepository.existsById(1)).thenReturn(false);
		
		assertThrows(EntityNotFoundException.class, () -> curvePointService.deleteCurvePoint(1));
	}
	
	@Test
	public void testDeleteCurvePoint() {
		when(curvePointRepository.existsById(1)).thenReturn(true);
		
		curvePointService.deleteCurvePoint(1);
		
		verify(curvePointRepository, times(1)).deleteById(1);
	}

}
