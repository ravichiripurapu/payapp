/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { DeductionTypeComponent } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.component';
import { DeductionTypeService } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.service';
import { DeductionType } from '../../../../../../main/webapp/app/entities/deduction-type/deduction-type.model';

describe('Component Tests', () => {

    describe('DeductionType Management Component', () => {
        let comp: DeductionTypeComponent;
        let fixture: ComponentFixture<DeductionTypeComponent>;
        let service: DeductionTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionTypeComponent],
                providers: [
                    DeductionTypeService
                ]
            })
            .overrideTemplate(DeductionTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new DeductionType(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.deductionTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
