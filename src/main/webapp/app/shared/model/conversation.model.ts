import { Moment } from 'moment';
import { IDynamicDataEnvelope } from 'app/shared/model/dynamic-data-envelope.model';
import { IAuditInfo } from 'app/shared/model/audit-info.model';
import { IDiscrim } from 'app/shared/model/discrim.model';
import { CONVERSATIONTYPE } from 'app/shared/model/enumerations/conversationtype.model';

export interface IConversation {
  id?: number;
  subject?: string;
  type?: CONVERSATIONTYPE;
  attachmentContentType?: string;
  attachment?: any;
  attachmentName?: string;
  status?: string;
  sentDateTime?: Moment;
  dynamicDataEnvelope?: IDynamicDataEnvelope;
  auditInfo?: IAuditInfo;
  discrim?: IDiscrim;
}

export class Conversation implements IConversation {
  constructor(
    public id?: number,
    public subject?: string,
    public type?: CONVERSATIONTYPE,
    public attachmentContentType?: string,
    public attachment?: any,
    public attachmentName?: string,
    public status?: string,
    public sentDateTime?: Moment,
    public dynamicDataEnvelope?: IDynamicDataEnvelope,
    public auditInfo?: IAuditInfo,
    public discrim?: IDiscrim
  ) {}
}
