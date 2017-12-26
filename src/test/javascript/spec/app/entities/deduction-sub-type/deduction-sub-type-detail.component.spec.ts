/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { PayappTestModule } from '../../../test.module';
import { DeductionSubTypeDetailComponent } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type-detail.component';
import { DeductionSubTypeService } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.service';
import { DeductionSubType } from '../../../../../../main/webapp/app/entities/deduction-sub-type/deduction-sub-type.model';

describe('Component Tests', () => {

    describe('DeductionSubType Management Detail Component', () => {
        let comp: DeductionSubTypeDetailComponent;
        let fixture: ComponentFixture<DeductionSubTypeDetailComponent>;
        let service: DeductionSubTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [DeductionSubTypeDetailComponent],
                providers: [
                    DeductionSubTypeService
                ]
            })
            .overrideTemplate(DeductionSubTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DeductionSubTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DeductionSubTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new DeductionSubType(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.deductionSubType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
