import { type ITypeOfArtmedia } from '@/shared/model/type-of-artmedia.model';
import { type ITypeOfArtist } from '@/shared/model/type-of-artist.model';
import { type IArtisticGenre } from '@/shared/model/artistic-genre.model';

export interface IArtist {
  id?: number;
  acronym?: string;
  publicName?: string;
  realName?: string | null;
  biographyDetailsJSON?: string | null;
  typeOfArtmedia?: ITypeOfArtmedia;
  typeOfArtist?: ITypeOfArtist;
  artistAggregator?: IArtist | null;
  artists?: IArtisticGenre[] | null;
}

export class Artist implements IArtist {
  constructor(
    public id?: number,
    public acronym?: string,
    public publicName?: string,
    public realName?: string | null,
    public biographyDetailsJSON?: string | null,
    public typeOfArtmedia?: ITypeOfArtmedia,
    public typeOfArtist?: ITypeOfArtist,
    public artistAggregator?: IArtist | null,
    public artists?: IArtisticGenre[] | null,
  ) {}
}
