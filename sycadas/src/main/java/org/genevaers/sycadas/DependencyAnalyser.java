package org.genevaers.sycadas;

/*
 * Copyright Contributors to the GenevaERS Project. SPDX-License-Identifier: Apache-2.0 (c) Copyright IBM Corporation 2008.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;
import org.genevaers.sycadas.dataprovider.SycadaDataProvider;

public interface DependencyAnalyser {

	public void generateDependencyDataFrom(SycadaDataProvider dataFromHere);
	public boolean hasDataErrors();
	public List<String> getDataErrors();
	public Set<Integer> getFieldIDs();
	public Map<Integer, List<Integer>> getLookupIDs();
	
}
