package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	public List<CurvePoint> getAllCurvePoints() {
		return curvePointRepository.findAll();
	}

	public Optional<CurvePoint> getCurvePointById(Integer id) {
		return curvePointRepository.findById(id);
	}

	public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	public CurvePoint updateCurvePoint(Integer id, CurvePoint updatedCurvePoint) {
		return curvePointRepository.findById(id)
				.map(curvePoint -> {
					curvePoint.setCurve_id(updatedCurvePoint.getCurve_id());
					curvePoint.setTerm(updatedCurvePoint.getTerm());
					curvePoint.setValue(updatedCurvePoint.getValue());
					return curvePointRepository.save(curvePoint);
				})
				.orElseThrow(() -> new EntityNotFoundException("Curve Point with id " + id + " not found"));
	}
	
	public void deleteCurvePoint(Integer id) {
		if(curvePointRepository.existsById(id)) {
			curvePointRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("Curve Point with id " + id + " not found");
		}
	}
}
