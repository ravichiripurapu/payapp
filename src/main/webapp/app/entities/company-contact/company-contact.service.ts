import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyContact } from './company-contact.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyContactService {

    private resourceUrl = SERVER_API_URL + 'api/company-contacts';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyContact: CompanyContact): Observable<CompanyContact> {
        const copy = this.convert(companyContact);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyContact: CompanyContact): Observable<CompanyContact> {
        const copy = this.convert(companyContact);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyContact> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CompanyContact.
     */
    private convertItemFromServer(json: any): CompanyContact {
        const entity: CompanyContact = Object.assign(new CompanyContact(), json);
        entity.dob = this.dateUtils
            .convertLocalDateFromServer(json.dob);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyContact to a JSON which can be sent to the server.
     */
    private convert(companyContact: CompanyContact): CompanyContact {
        const copy: CompanyContact = Object.assign({}, companyContact);
        copy.dob = this.dateUtils
            .convertLocalDateToServer(companyContact.dob);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyContact.createdDate);
        return copy;
    }
}
