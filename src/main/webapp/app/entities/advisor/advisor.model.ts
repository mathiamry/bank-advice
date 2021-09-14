import { IUser } from 'app/entities/user/user.model';
import { IAgency } from 'app/entities/agency/agency.model';
import { IAppointment } from 'app/entities/appointment/appointment.model';
import { Gender } from 'app/entities/enumerations/gender.model';

export interface IAdvisor {
  id?: number;
  gender?: Gender | null;
  telephone?: string;
  user?: IUser;
  agency?: IAgency | null;
  appointments?: IAppointment[] | null;
}

export class Advisor implements IAdvisor {
  constructor(
    public id?: number,
    public gender?: Gender | null,
    public telephone?: string,
    public user?: IUser,
    public agency?: IAgency | null,
    public appointments?: IAppointment[] | null
  ) {}
}

export function getAdvisorIdentifier(advisor: IAdvisor): number | undefined {
  return advisor.id;
}
