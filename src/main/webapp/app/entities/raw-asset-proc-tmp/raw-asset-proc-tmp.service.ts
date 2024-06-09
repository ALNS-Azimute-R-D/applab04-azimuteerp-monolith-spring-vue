import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { type IRawAssetProcTmp } from '@/shared/model/raw-asset-proc-tmp.model';

const baseApiUrl = 'api/raw-asset-proc-tmps';

export default class RawAssetProcTmpService {
  public find(id: number): Promise<IRawAssetProcTmp> {
    return new Promise<IRawAssetProcTmp>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IRawAssetProcTmp): Promise<IRawAssetProcTmp> {
    return new Promise<IRawAssetProcTmp>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IRawAssetProcTmp): Promise<IRawAssetProcTmp> {
    return new Promise<IRawAssetProcTmp>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IRawAssetProcTmp): Promise<IRawAssetProcTmp> {
    return new Promise<IRawAssetProcTmp>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
