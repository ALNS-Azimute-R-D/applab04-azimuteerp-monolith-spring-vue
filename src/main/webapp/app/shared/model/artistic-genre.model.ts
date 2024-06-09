import { type IArtist } from '@/shared/model/artist.model';

export interface IArtisticGenre {
  id?: number;
  acronym?: string;
  name?: string;
  description?: string;
  artisticGenres?: IArtist[] | null;
}

export class ArtisticGenre implements IArtisticGenre {
  constructor(
    public id?: number,
    public acronym?: string,
    public name?: string,
    public description?: string,
    public artisticGenres?: IArtist[] | null,
  ) {}
}
