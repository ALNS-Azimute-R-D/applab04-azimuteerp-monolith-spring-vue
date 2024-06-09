import { type IAsset } from '@/shared/model/asset.model';

export interface IAssetMetadata {
  id?: number;
  metadataDetailsJSON?: string | null;
  asset?: IAsset;
}

export class AssetMetadata implements IAssetMetadata {
  constructor(
    public id?: number,
    public metadataDetailsJSON?: string | null,
    public asset?: IAsset,
  ) {}
}
