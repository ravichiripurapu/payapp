/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { CompanyContactTypeComponent } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.component';
import { CompanyContactTypeService } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.service';
import { CompanyContactType } from '../../../../../../main/webapp/app/entities/company-contact-type/company-contact-type.model';

describe('Component Tests', () => {

    describe('CompanyContactType Management Component', () => {
        let comp: CompanyContactTypeComponent;
        let fixture: ComponentFixture<CompanyContactTypeComponent>;
        let service: CompanyContactTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [CompanyContactTypeComponent],
                providers: [
                    CompanyContactTypeService
                ]
            })
            .overrideTemplate(CompanyContactTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CompanyContactTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompanyContactTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CompanyContactType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.companyContactTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
