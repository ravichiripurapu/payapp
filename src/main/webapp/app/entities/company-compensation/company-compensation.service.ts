import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyCompensation } from './company-compensation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyCompensationService {

    private resourceUrl = SERVER_API_URL + 'api/company-compensations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyCompensation: CompanyCompensation): Observable<CompanyCompensation> {
        const copy = this.convert(companyCompensation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyCompensation: CompanyCompensation): Observable<CompanyCompensation> {
        const copy = this.convert(companyCompensation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyCompensation> {
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
     * Convert a returned JSON object to CompanyCompensation.
     */
    private convertItemFromServer(json: any): CompanyCompensation {
        const entity: CompanyCompensation = Object.assign(new CompanyCompensation(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyCompensation to a JSON which can be sent to the server.
     */
    private convert(companyCompensation: CompanyCompensation): CompanyCompensation {
        const copy: CompanyCompensation = Object.assign({}, companyCompensation);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyCompensation.createdDate);
        return copy;
    }
}
