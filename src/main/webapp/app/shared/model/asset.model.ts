import { type IAssetType } from '@/shared/model/asset-type.model';
import { type IRawAssetProcTmp } from '@/shared/model/raw-asset-proc-tmp.model';
import { type IAssetCollection } from '@/shared/model/asset-collection.model';

import { type StorageTypeEnum } from '@/shared/model/enumerations/storage-type-enum.model';
import { type StatusAssetEnum } from '@/shared/model/enumerations/status-asset-enum.model';
import { type PreferredPurposeEnum } from '@/shared/model/enumerations/preferred-purpose-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IAsset {
  id?: number;
  name?: string;
  storageTypeUsed?: keyof typeof StorageTypeEnum | null;
  fullFilenamePath?: string | null;
  status?: keyof typeof StatusAssetEnum | null;
  preferredPurpose?: keyof typeof PreferredPurposeEnum | null;
  assetContentAsBlobContentType?: string | null;
  assetContentAsBlob?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  assetType?: IAssetType;
  rawAssetProcTmp?: IRawAssetProcTmp | null;
  assetCollections?: IAssetCollection[] | null;
}

export class Asset implements IAsset {
  constructor(
    public id?: number,
    public name?: string,
    public storageTypeUsed?: keyof typeof StorageTypeEnum | null,
    public fullFilenamePath?: string | null,
    public status?: keyof typeof StatusAssetEnum | null,
    public preferredPurpose?: keyof typeof PreferredPurposeEnum | null,
    public assetContentAsBlobContentType?: string | null,
    public assetContentAsBlob?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public assetType?: IAssetType,
    public rawAssetProcTmp?: IRawAssetProcTmp | null,
    public assetCollections?: IAssetCollection[] | null,
  ) {}
}
