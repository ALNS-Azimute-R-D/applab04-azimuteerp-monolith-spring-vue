export interface ICategory {
  id?: number;
  acronym?: string | null;
  name?: string;
  description?: string | null;
  handlerClazzName?: string | null;
  categoryParent?: ICategory | null;
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public acronym?: string | null,
    public name?: string,
    public description?: string | null,
    public handlerClazzName?: string | null,
    public categoryParent?: ICategory | null,
  ) {}
}
