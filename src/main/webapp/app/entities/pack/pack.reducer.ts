import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPack, defaultValue } from 'app/shared/model/pack.model';

export const ACTION_TYPES = {
  FETCH_PACK_LIST: 'pack/FETCH_PACK_LIST',
  FETCH_PACK: 'pack/FETCH_PACK',
  CREATE_PACK: 'pack/CREATE_PACK',
  UPDATE_PACK: 'pack/UPDATE_PACK',
  DELETE_PACK: 'pack/DELETE_PACK',
  SET_BLOB: 'pack/SET_BLOB',
  RESET: 'pack/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPack>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PackState = Readonly<typeof initialState>;

// Reducer

export default (state: PackState = initialState, action): PackState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PACK_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PACK):
    case REQUEST(ACTION_TYPES.UPDATE_PACK):
    case REQUEST(ACTION_TYPES.DELETE_PACK):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PACK_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PACK):
    case FAILURE(ACTION_TYPES.CREATE_PACK):
    case FAILURE(ACTION_TYPES.UPDATE_PACK):
    case FAILURE(ACTION_TYPES.DELETE_PACK):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PACK_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PACK):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PACK):
    case SUCCESS(ACTION_TYPES.UPDATE_PACK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PACK):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/packs';

// Actions

export const getEntities: ICrudGetAllAction<IPack> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PACK_LIST,
  payload: axios.get<IPack>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPack> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PACK,
    payload: axios.get<IPack>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPack> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PACK,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPack> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PACK,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPack> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PACK,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
