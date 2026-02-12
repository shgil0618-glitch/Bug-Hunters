import { runSaga } from 'redux-saga';
import { loadMaterials } from '../materialSaga'; // 워커 사가 함수
import * as api from '../../api/axios'; // loadMaterialsAPI가 있는 파일
import { 
  LOAD_MATERIALS_SUCCESS, 
  LOAD_MATERIALS_FAILURE 
} from '../../reducers/material';

describe('Material Saga 테스트', () => {
  
  // 1. 성공 케이스 테스트
  test('데이터 로딩 성공 시 SUCCESS 액션을 디스패치해야 한다', async () => {
    const mockResponse = {
      data: [{ materialid: 1, title: '테스트 재료', category: '테스트' }],
    };

    // api/axios.js의 loadMaterialsAPI를 가로챔
    const requestSpy = jest.spyOn(api, 'loadMaterialsAPI').mockImplementation(() => 
      Promise.resolve(mockResponse)
    );
    
    const dispatched = [];
    await runSaga(
      {
        dispatch: (action) => dispatched.push(action),
      },
      loadMaterials, // 테스트할 워커 사가
      { data: 1 }    // 액션 객체
    ).toPromise();

    expect(dispatched).toContainEqual({
      type: LOAD_MATERIALS_SUCCESS,
      data: mockResponse.data,
    });
    expect(requestSpy).toHaveBeenCalled();
    
    requestSpy.mockRestore();
  });

  // 2. 실패 케이스 테스트
  test('데이터 로딩 실패 시 FAILURE 액션을 디스패치해야 한다', async () => {
    const errorResponse = { response: { data: '에러 발생' } };
    
    const requestSpy = jest.spyOn(api, 'loadMaterialsAPI').mockImplementation(() => 
      Promise.reject(errorResponse)
    );
    
    const dispatched = [];
    await runSaga(
      {
        dispatch: (action) => dispatched.push(action),
      },
      loadMaterials,
      { data: 1 }
    ).toPromise();

    expect(dispatched).toContainEqual({
      type: LOAD_MATERIALS_FAILURE,
      error: '에러 발생',
    });
    
    requestSpy.mockRestore();
  });
});