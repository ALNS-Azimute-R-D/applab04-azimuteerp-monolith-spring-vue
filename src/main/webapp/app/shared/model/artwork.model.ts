import { type ITypeOfArtmedia } from '@/shared/model/type-of-artmedia.model';

export interface IArtwork {
  id?: number;
  artworkTitle?: string;
  productionYear?: number | null;
  seasonNumber?: number | null;
  episodeOrSequenceNumber?: number | null;
  registerIdInIMDB?: string | null;
  assetsCollectionUUID?: string | null;
  contentDetailsJSON?: string | null;
  typeOfArtmedia?: ITypeOfArtmedia;
  artworkAggregator?: IArtwork | null;
}

export class Artwork implements IArtwork {
  constructor(
    public id?: number,
    public artworkTitle?: string,
    public productionYear?: number | null,
    public seasonNumber?: number | null,
    public episodeOrSequenceNumber?: number | null,
    public registerIdInIMDB?: string | null,
    public assetsCollectionUUID?: string | null,
    public contentDetailsJSON?: string | null,
    public typeOfArtmedia?: ITypeOfArtmedia,
    public artworkAggregator?: IArtwork | null,
  ) {}
}
