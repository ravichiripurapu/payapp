/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyContactComponent } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.component';
import { CompanyContactService } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.service';
import { CompanyContact } from '../../../../../../main/webapp/app/entities/company-contact/company-contact.model';

describe('Component Tests', () => {

    describe('CompanyContact Management Component', () => {
        let comp: CompanyContactComponent;
        let fixture: ComponentFixture<CompanyContactComponent>;
        let service: CompanyContactService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyContactComponent],
                providers: [
                    CompanyContactService
                ]
            })
            .overrideTemplate(CompanyContactComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyContactComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyContactService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyContact(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyContacts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
