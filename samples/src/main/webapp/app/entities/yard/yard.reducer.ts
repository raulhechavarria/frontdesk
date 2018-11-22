import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IYard, defaultValue } from 'app/shared/model/yard.model';

export const ACTION_TYPES = {
  FETCH_YARD_LIST: 'yard/FETCH_YARD_LIST',
  FETCH_YARD: 'yard/FETCH_YARD',
  CREATE_YARD: 'yard/CREATE_YARD',
  UPDATE_YARD: 'yard/UPDATE_YARD',
  DELETE_YARD: 'yard/DELETE_YARD',
  RESET: 'yard/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IYard>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type YardState = Readonly<typeof initialState>;

// Reducer

export default (state: YardState = initialState, action): YardState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_YARD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_YARD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_YARD):
    case REQUEST(ACTION_TYPES.UPDATE_YARD):
    case REQUEST(ACTION_TYPES.DELETE_YARD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_YARD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_YARD):
    case FAILURE(ACTION_TYPES.CREATE_YARD):
    case FAILURE(ACTION_TYPES.UPDATE_YARD):
    case FAILURE(ACTION_TYPES.DELETE_YARD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_YARD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_YARD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_YARD):
    case SUCCESS(ACTION_TYPES.UPDATE_YARD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_YARD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/yards';

// Actions

export const getEntities: ICrudGetAllAction<IYard> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_YARD_LIST,
  payload: axios.get<IYard>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IYard> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_YARD,
    payload: axios.get<IYard>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IYard> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_YARD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IYard> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_YARD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IYard> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_YARD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
