// reducers/material.js

export const initialState = {
  mainMaterials: [],
  loadMaterialsLoading: false,
  loadMaterialsDone: false,
  loadMaterialsError: null,
};

export const LOAD_MATERIALS_REQUEST = 'LOAD_MATERIALS_REQUEST';
export const LOAD_MATERIALS_SUCCESS = 'LOAD_MATERIALS_SUCCESS';
export const LOAD_MATERIALS_FAILURE = 'LOAD_MATERIALS_FAILURE';

export default function materialReducer(state = initialState, action) {
  switch (action.type) {
    case LOAD_MATERIALS_REQUEST:
      return { ...state, loadMaterialsLoading: true, loadMaterialsDone: false, loadMaterialsError: null };
    case LOAD_MATERIALS_SUCCESS:
      return { ...state, loadMaterialsLoading: false, loadMaterialsDone: true, mainMaterials: action.data };
    case LOAD_MATERIALS_FAILURE:
      return { ...state, loadMaterialsLoading: false, loadMaterialsError: action.error };
    default:
      return state;
  }
}