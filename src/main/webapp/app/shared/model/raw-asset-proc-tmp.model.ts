import { type IAssetType } from '@/shared/model/asset-type.model';

import { type StatusRawProcessingEnum } from '@/shared/model/enumerations/status-raw-processing-enum.model';
export interface IRawAssetProcTmp {
  id?: number;
  name?: string;
  statusRawProcessing?: keyof typeof StatusRawProcessingEnum | null;
  fullFilenamePath?: string | null;
  assetRawContentAsBlobContentType?: string | null;
  assetRawContentAsBlob?: string | null;
  customAttributesDetailsJSON?: string | null;
  assetType?: IAssetType;
}

export class RawAssetProcTmp implements IRawAssetProcTmp {
  constructor(
    public id?: number,
    public name?: string,
    public statusRawProcessing?: keyof typeof StatusRawProcessingEnum | null,
    public fullFilenamePath?: string | null,
    public assetRawContentAsBlobContentType?: string | null,
    public assetRawContentAsBlob?: string | null,
    public customAttributesDetailsJSON?: string | null,
    public assetType?: IAssetType,
  ) {}
}
