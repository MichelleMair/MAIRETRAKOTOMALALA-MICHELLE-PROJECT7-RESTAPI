package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	public List<RuleName> getAllRuleNames() {
		return ruleNameRepository.findAll();
	}

	public Optional<RuleName> getRuleNameById(Integer id) {
		return ruleNameRepository.findById(id);
	}

	public RuleName saveRuleName(@Valid RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}
	
	
	public RuleName updateRuleName(Integer id, @Valid RuleName updatedRuleName) {
		return ruleNameRepository.findById(id)
				.map(ruleName -> {
					ruleName.setName(updatedRuleName.getName());
					ruleName.setDescription(updatedRuleName.getDescription());
					ruleName.setJson(updatedRuleName.getJson());
					ruleName.setTemplate(updatedRuleName.getTemplate());
					ruleName.setSqlstr(updatedRuleName.getSqlstr());
					ruleName.setSqlpart(updatedRuleName.getSqlpart());
					return ruleNameRepository.save(ruleName);
				})
				.orElseThrow(() -> new EntityNotFoundException("RuleName with id " + id + " not found"));
	}

	public void deleteRuleName(Integer id) {
		if (ruleNameRepository.existsById(id)) {
			ruleNameRepository.deleteById(id);
		} else {
			throw new EntityNotFoundException("RuleName with id " + id + " not found");
		}
	}

}
