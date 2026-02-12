import reducer, { 
  initialState, 
  LOAD_MATERIALS_REQUEST, 
  LOAD_MATERIALS_SUCCESS,
  LOAD_MATERIALS_FAILURE 
} from '../material'; // 경로가 ../material.js가 맞는지 확인하세요!

describe('Material Reducer 테스트', () => {
  
  // 1. 초기 상태 확인
  test('초기 상태를 반환해야 한다', () => {
    expect(reducer(undefined, {})).toEqual(initialState);
  });

  // 2. 요청 시작 시 로딩 상태 확인
  test('LOAD_MATERIALS_REQUEST 발생 시 로딩 상태가 true가 되어야 한다', () => {
    const state = reducer(initialState, { type: LOAD_MATERIALS_REQUEST });
    expect(state.loadMaterialsLoading).toBe(true);
    expect(state.loadMaterialsDone).toBe(false);
  });

  // 3. 성공 시 데이터 저장 확인
  test('LOAD_MATERIALS_SUCCESS 발생 시 데이터가 저장되어야 한다', () => {
    const mockData = [
      { materialid: 1, title: '당근', category: '채소' },
      { materialid: 2, title: '소고기', category: '육류' }
    ];
    const state = reducer(initialState, { 
      type: LOAD_MATERIALS_SUCCESS, 
      data: mockData 
    });
    expect(state.loadMaterialsLoading).toBe(false);
    expect(state.loadMaterialsDone).toBe(true);
    expect(state.mainMaterials).toEqual(mockData);
    expect(state.mainMaterials).toHaveLength(2);
  });

  // 4. 실패 시 에러 처리 확인
  test('LOAD_MATERIALS_FAILURE 발생 시 에러 메시지가 담겨야 한다', () => {
    const errorReason = '서버 에러 발생';
    const state = reducer(initialState, { 
      type: LOAD_MATERIALS_FAILURE, 
      error: errorReason 
    });
    expect(state.loadMaterialsLoading).toBe(false);
    expect(state.loadMaterialsError).toBe(errorReason);
  });

});