import { type IArtwork } from '@/shared/model/artwork.model';
import { type IArtist } from '@/shared/model/artist.model';

export interface IArtworkCast {
  id?: number;
  orderOfAppearance?: number | null;
  characterName?: string | null;
  mainAssetUUID?: string | null;
  characterDetailsJSON?: string | null;
  artwork?: IArtwork;
  artist?: IArtist;
}

export class ArtworkCast implements IArtworkCast {
  constructor(
    public id?: number,
    public orderOfAppearance?: number | null,
    public characterName?: string | null,
    public mainAssetUUID?: string | null,
    public characterDetailsJSON?: string | null,
    public artwork?: IArtwork,
    public artist?: IArtist,
  ) {}
}
