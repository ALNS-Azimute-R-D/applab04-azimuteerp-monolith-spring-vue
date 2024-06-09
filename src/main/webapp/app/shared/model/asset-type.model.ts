export interface IAssetType {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string | null;
  handlerClazzName?: string | null;
  customAttributesDetailsJSON?: string | null;
}

export class AssetType implements IAssetType {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string | null,
    public handlerClazzName?: string | null,
    public customAttributesDetailsJSON?: string | null,
  ) {}
}
