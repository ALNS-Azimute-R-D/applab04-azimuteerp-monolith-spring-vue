import { type ITypeOfPerson } from '@/shared/model/type-of-person.model';
import { type IDistrict } from '@/shared/model/district.model';

import { type GenderEnum } from '@/shared/model/enumerations/gender-enum.model';
import { type ActivationStatusEnum } from '@/shared/model/enumerations/activation-status-enum.model';
export interface IPerson {
  id?: number;
  firstname?: string;
  lastname?: string;
  fullname?: string | null;
  birthDate?: Date;
  gender?: keyof typeof GenderEnum;
  codeBI?: string | null;
  codeNIF?: string | null;
  streetAddress?: string;
  houseNumber?: string | null;
  locationName?: string | null;
  postalCode?: string;
  mainEmail?: string;
  landPhoneNumber?: string | null;
  mobilePhoneNumber?: string | null;
  occupation?: string | null;
  preferredLanguage?: string | null;
  usernameInOAuth2?: string | null;
  userIdInOAuth2?: string | null;
  customAttributesDetailsJSON?: string | null;
  activationStatus?: keyof typeof ActivationStatusEnum;
  avatarImgContentType?: string | null;
  avatarImg?: string | null;
  typeOfPerson?: ITypeOfPerson;
  district?: IDistrict | null;
  managerPerson?: IPerson | null;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public firstname?: string,
    public lastname?: string,
    public fullname?: string | null,
    public birthDate?: Date,
    public gender?: keyof typeof GenderEnum,
    public codeBI?: string | null,
    public codeNIF?: string | null,
    public streetAddress?: string,
    public houseNumber?: string | null,
    public locationName?: string | null,
    public postalCode?: string,
    public mainEmail?: string,
    public landPhoneNumber?: string | null,
    public mobilePhoneNumber?: string | null,
    public occupation?: string | null,
    public preferredLanguage?: string | null,
    public usernameInOAuth2?: string | null,
    public userIdInOAuth2?: string | null,
    public customAttributesDetailsJSON?: string | null,
    public activationStatus?: keyof typeof ActivationStatusEnum,
    public avatarImgContentType?: string | null,
    public avatarImg?: string | null,
    public typeOfPerson?: ITypeOfPerson,
    public district?: IDistrict | null,
    public managerPerson?: IPerson | null,
  ) {}
}
